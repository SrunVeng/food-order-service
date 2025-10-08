package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MenuCreateRequestVo {

    @NotBlank(message ="name cannot be blank")
    private String name;
    @NotBlank(message ="price cannot be blank")
    private String basePrice;

    private String description;
}
