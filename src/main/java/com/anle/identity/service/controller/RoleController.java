package com.anle.identity.service.controller;

import com.anle.identity.service.dto.ApiResponse;
import com.anle.identity.service.dto.role.RoleRequest;
import com.anle.identity.service.dto.role.RoleResponse;
import com.anle.identity.service.service.RoleService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder().result(roleService.createRole(roleRequest)).build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder().result(roleService.getAllRoles()).build();
    }

    @DeleteMapping("/{role}")
    public ApiResponse<String> delete(@PathVariable("role") String role) {
        roleService.delete(role);
        return ApiResponse.<String>builder().result("Role is deleted").build();
    }
}
