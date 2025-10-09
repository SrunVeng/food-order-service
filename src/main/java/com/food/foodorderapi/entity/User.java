package com.food.foodorderapi.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.foodorderapi.library.UserID.UserId;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UserId
    private String userNo;

    private String username;
    private String password;

    private String firstName;
    private String lastName;

    //TODO add this field to client
    private String gender;
    private String dob;

    private String email;
    private String phoneNumber;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    private String ChatId;

    // Security & lifecycle
    private Boolean isVerified = Boolean.FALSE;
    private Boolean isBlock = Boolean.FALSE;
    private Boolean isAccountNonExpired = Boolean.TRUE;
    private Boolean isAccountNonLocked = Boolean.TRUE;
    private Boolean isCredentialsNonExpired = Boolean.TRUE;
    private Boolean isDeleted = Boolean.FALSE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private List<Role> roles;




}
