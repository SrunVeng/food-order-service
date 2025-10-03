package com.food.foodorderapi.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "otp")
public class PasswordResetOTP {

    @Id
    private Long id;


    private String email;

    private String otp;

    @Column(columnDefinition = "timestamp with time zone")
    private Instant createdAt;

    @Column(columnDefinition = "timestamp with time zone")
    private Instant  expiresAt;


}
