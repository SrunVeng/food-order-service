
package com.food.foodorderapi.dto.response;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

import com.food.foodorderapi.entity.Role;

@Data
public class AdminResultDto {

    private String userNo;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Instant createdAt;
    private String ChatId;
    private List<Role> roles;
}
