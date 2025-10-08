package com.food.foodorderapi.vo.response;


import lombok.Data;

import java.time.Instant;
import java.util.List;

import com.food.foodorderapi.entity.Role;

@Data
public class AdminResponseVo {

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
