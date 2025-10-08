package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminDeleteRequestVo {

    @NotBlank(message = "user number cannot be blank")
    private String userNo;
}
