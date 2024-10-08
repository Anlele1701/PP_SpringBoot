package com.anle.identity.service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "Users")
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "username")
    String username;
    @Column(name = "password")
    String password;
    @Column(name = "firstName")
    String firstName;
    @Column(name = "lastName")
    String lastName;
    @Column(name = "balance", columnDefinition = "FLOAT DEFAULT 0")
    float balance;
    @Column(name = "dob")
    LocalDate dob;
    @ManyToMany
    @Column(name = "roles")
    Set<Role> roles;
}
