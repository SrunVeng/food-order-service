package com.food.foodorderapi.dto.request;

import lombok.Data;

@Data
public class UserUpdateProfileVerifyRequestDto {

    private String email;
    private String otp;
}
