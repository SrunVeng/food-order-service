package com.food.foodorderapi.dto.request;



import lombok.Data;

@Data
public class AdminSetPasswordRequestDto {

    private String token;
    private String password;
}
