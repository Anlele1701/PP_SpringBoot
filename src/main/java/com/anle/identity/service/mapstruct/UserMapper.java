package com.anle.identity.service.mapstruct;

import com.anle.identity.service.dto.user.request.UserCreationRequest;
import com.anle.identity.service.dto.user.request.UserUpdateRequest;
import com.anle.identity.service.dto.user.response.UserResponse;
import com.anle.identity.service.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
