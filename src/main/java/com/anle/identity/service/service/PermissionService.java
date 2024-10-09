package com.anle.identity.service.service;

import com.anle.identity.service.dto.permission.PermissionRequest;
import com.anle.identity.service.dto.permission.PermissionResponse;
import com.anle.identity.service.entity.Permission;
import com.anle.identity.service.exception.AppException;
import com.anle.identity.service.exception.ErrorCode;
import com.anle.identity.service.mapstruct.PermissionMapper;
import com.anle.identity.service.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        log.info("{} creating new permission: {}", getClass(), request);
        Permission permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getAllPermissions() {
        log.info("{} getting all permission", getClass());
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String id) {
        log.info("{} deleteting permission with id: {}", getClass(), id);
        permissionRepository.findById(id).orElseThrow(() -> {
            log.error("{} deleting user not found with ID: {}", getClass(), id);
            return new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        });
        permissionRepository.deleteById(id);
    }

}
