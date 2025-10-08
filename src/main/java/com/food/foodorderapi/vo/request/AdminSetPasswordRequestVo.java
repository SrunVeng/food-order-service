package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminSetPasswordRequestVo {

    @NotBlank(message = "token cannot be blank")
    private String token;
    @NotBlank(message = "password be blank")
    private String password;



}
