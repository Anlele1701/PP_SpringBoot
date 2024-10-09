package com.anle.identity.service.repository;

import com.anle.identity.service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, String> {
}
