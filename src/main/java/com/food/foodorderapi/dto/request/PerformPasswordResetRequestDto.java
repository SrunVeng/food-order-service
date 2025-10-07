package com.food.foodorderapi.dto.request;



import lombok.Data;

@Data
public class PerformPasswordResetRequestDto {


    private String token;
    private String newPassword;
    private String confirmPassword;

}
