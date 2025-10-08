package com.food.foodorderapi.dto.request;



import lombok.Data;

@Data
public class MenuCreateRequestDto {

    private String name;
    private String description;
    private String basePrice;



}
