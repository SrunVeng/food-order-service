package com.food.foodorderapi.dto.request;


import lombok.Data;

@Data
public class MenuUpdateRequestDto {
    private Long id;
    private String name;
    private String description;
    private String basePrice;
}
