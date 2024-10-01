package com.anle.identity.service.service;

import com.anle.identity.service.dto.user.request.UserCreationRequest;
import com.anle.identity.service.dto.user.request.UserUpdateRequest;
import com.anle.identity.service.dto.user.response.UserResponse;
import com.anle.identity.service.entity.User;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RabbitMQProducer rabbitMQProducer;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserResponse createUser(UserCreationRequest request) {
        logger.info(getClass() + " creating user with input: {} ", userMapper.toUser(request));
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // rabbitMQProducer.sendMessage("User created!");
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers() {
        logger.info(getClass() + " get user list");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }


    public UserResponse updateUser(String id, UserUpdateRequest request) {
        logger.info("{} updating user with id: {} with input: {}", getClass(), id, userMapper.toUser(request));
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getUser(String id) {
        logger.info("{} get user with id: {}", getClass(), id);
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("{} getting user not found with ID: {}", getClass(), id);
                    return new AppException(ErrorCode.USER_NOT_EXISTED);
                }));
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
