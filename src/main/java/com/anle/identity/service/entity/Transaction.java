package com.anle.identity.service.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;
    private Float amount;
    private LocalDateTime timestamp;

}
