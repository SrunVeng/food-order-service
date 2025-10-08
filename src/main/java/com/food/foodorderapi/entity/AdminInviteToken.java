package com.food.foodorderapi.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "admin_invite")
public class AdminInviteToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 128)
    private String token;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    public enum Status { OPEN, USED, EXPIRED }

}
