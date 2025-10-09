package com.food.foodorderapi.dto.request;


import lombok.Data;

@Data
public class AdminCreateRequestDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;

}
