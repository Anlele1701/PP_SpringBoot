package com.anle.identity.service.repository;

import com.anle.identity.service.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
//    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
