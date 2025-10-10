package com.food.foodorderapi.dto.request;


import lombok.Data;

@Data
public class UserUpdateProfileRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String dob;
}
