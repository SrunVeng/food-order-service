package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminDeleteRequestVo {

    @NotBlank(message = "username cannot be blank")
    private String username;
}
