package com.food.foodorderapi.dto.request;


import lombok.Data;

@Data
public class UserRegisterVerifyRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String otp;

}
