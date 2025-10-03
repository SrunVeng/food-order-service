package com.food.foodorderapi.dto.request;


import lombok.Data;

@Data
public class UserLoginRequestDto {

    private String username;
    private String password;
}
