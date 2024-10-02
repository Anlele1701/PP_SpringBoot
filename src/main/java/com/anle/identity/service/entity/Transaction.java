package com.anle.identity.service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Transaction")
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "senderID")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "recipientID")
    private User recipient;
    private Float amount;
}
