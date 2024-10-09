package com.anle.identity.service.mapstruct;

import com.anle.identity.service.dto.permission.PermissionRequest;
import com.anle.identity.service.dto.permission.PermissionResponse;
import com.anle.identity.service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
