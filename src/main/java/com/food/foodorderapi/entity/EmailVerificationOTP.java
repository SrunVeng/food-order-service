package com.food.foodorderapi.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "otp")
public class EmailVerificationOTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String email;

    private String otp;


    private Instant createdAt;

    private Instant  expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Purpose purpose = Purpose.REGISTER;

    private int attempts = 0;

    public enum Status { OPEN, USED, EXPIRED }
    public enum Purpose { REGISTER, RESET_PASSWORD,UPDATE_EMAIL }



}
