package com.anle.identity.service.service;

import com.anle.identity.service.dto.user.request.UserCreationRequest;
import com.anle.identity.service.dto.user.request.UserUpdateRequest;
import com.anle.identity.service.dto.user.response.UserResponse;
import com.anle.identity.service.entity.User;
import com.anle.identity.service.enums.Role;
import com.anle.identity.service.exception.AppException;
import com.anle.identity.service.exception.ErrorCode;
import com.anle.identity.service.mapstruct.UserMapper;
import com.anle.identity.service.publisher.RabbitMQProducer;
import com.anle.identity.service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        logger.info(getClass() + " receiving request: {}", request.getBalance());
        logger.info(getClass() + " creating user with input: {} ", userMapper.toUser(request));
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
//        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        logger.info(getClass() + " get user list");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }


    public UserResponse updateUser(String id, UserUpdateRequest request) {
        logger.info("{} updating user with id: {} with input: {}", getClass(), id, userMapper.toUser(request));
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void transferMoney(String senderId, String recipientId, Float amount) {
        User sender = userRepository.findById(senderId).orElseThrow(() ->
        {
            logger.error("{} sender with id: {} cannot found", getClass(), senderId);
            return new RuntimeException("Sender not found");
        });
        User recipient = userRepository.findById(recipientId).orElseThrow(() ->
        {
            logger.error("{} recipient with id: {} cannot found", getClass(), senderId);
            return new RuntimeException("Recipient not found");
        });
        float senderAmount = sender.getBalance() - amount;
        float recipientAmount = recipient.getBalance() + amount;
        sender.setBalance(senderAmount);
        recipient.setBalance(recipientAmount);
        logger.info("{} sender amount: {}, recipient amount: {} ", getClass(), senderAmount, recipientAmount);
        userRepository.saveAll(Arrays.asList(sender, recipient));
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id) {
        logger.info("{} get user with id: {}", getClass(), id);
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("{} getting user not found with ID: {}", getClass(), id);
                    return new AppException(ErrorCode.USER_NOT_EXISTED);
                }));
    }
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        logger.info("{} get self info with username: {}", getClass(), name);

        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }
    public void deleteUser(String userId) {
        logger.info("{} deleting user with id: {}", getClass(), userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("{} deleting user not found with ID: {}", getClass(), userId);
                    return new AppException(ErrorCode.USER_NOT_EXISTED);
                });
        userRepository.deleteById(userId);
    }
}
