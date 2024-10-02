package com.anle.identity.service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Users")
@EqualsAndHashCode(callSuper = true)

public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name ="balance", columnDefinition = "FLOAT DEFAULT 500")
    private float balance;
    @Column(name = "dob")
    private LocalDate dob;
}
