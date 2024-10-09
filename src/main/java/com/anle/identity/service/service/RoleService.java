package com.anle.identity.service.service;

import com.anle.identity.service.dto.role.RoleRequest;
import com.anle.identity.service.dto.role.RoleResponse;
import com.anle.identity.service.exception.AppException;
import com.anle.identity.service.exception.ErrorCode;
import com.anle.identity.service.mapstruct.RoleMapper;
import com.anle.identity.service.repository.PermissionRepository;
import com.anle.identity.service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest roleRequest) {
        log.info("{} creating role: {}", getClass(), roleRequest);
        var role = roleMapper.toRole(roleRequest);
        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> getAllRoles() {
        log.info("{} get all role", getClass());
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void delete(String name) {
        log.info("{} deleting role: {}", getClass(), name);
        roleRepository.findById(name).orElseThrow( () -> {
            log.error("{} deleting role not found with name: {}",getClass(), name);
            return new AppException(ErrorCode.ROLE_NOT_EXISTED);
        } );

    }
}
