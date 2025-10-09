package com.food.foodorderapi.dto.request;


import lombok.Data;

@Data
public class AdminUpdateVerifyRequestDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String otp;

}
