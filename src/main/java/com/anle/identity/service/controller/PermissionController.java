package com.anle.identity.service.controller;

import com.anle.identity.service.dto.ApiResponse;
import com.anle.identity.service.dto.permission.PermissionRequest;
import com.anle.identity.service.dto.permission.PermissionResponse;
import com.anle.identity.service.service.PermissionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionController {
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAllPermissions())
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePermission(@PathVariable("id") String id) {
        permissionService.delete(id);
        return ApiResponse.<String>builder()
                .result("Permission has been deleted")
                .build();
    }
}


