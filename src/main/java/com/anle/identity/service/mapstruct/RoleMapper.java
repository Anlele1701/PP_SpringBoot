package com.anle.identity.service.mapstruct;

import com.anle.identity.service.dto.role.RoleRequest;
import com.anle.identity.service.dto.role.RoleResponse;
import com.anle.identity.service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
