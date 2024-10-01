package com.anle.identity.service.service;

import com.anle.identity.service.dto.authentication.AuthenticationRequest;
import com.anle.identity.service.dto.authentication.AuthenticationResponse;
import com.anle.identity.service.dto.introspect.IntrospectRequest;
import com.anle.identity.service.dto.introspect.IntrospectResponse;
import com.anle.identity.service.exception.AppException;
import com.anle.identity.service.exception.ErrorCode;
import com.anle.identity.service.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public IntrospectResponse introspectResponse(IntrospectRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();
        logger.info(getClass() + " introspecting with input: {} ", token);
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.info(getClass() + " authenticate with username: {} ", request.getUsername());
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(()
                ->
        {
            logger.error(getClass() + " user not found with username: {}", request.getUsername());
            return new AppException(ErrorCode.USER_NOT_EXISTED);
        });
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            logger.error(getClass() + " user is unauthenticated with username: {}", request.getUsername());
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generatedToken(request.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generatedToken(String username) {
        logger.info(getClass() + " generate with username: {} ", username);
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("domain.com.vn")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("Custom clam", "Custom")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            logger.error(getClass() + " generate error with username:{}", username);
            throw new RuntimeException(e);
        }
    }
}
