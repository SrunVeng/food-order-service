package com.food.foodorderapi.dto.request;


import lombok.Data;

@Data
public class AdminUpdateRequestDto {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;

}
