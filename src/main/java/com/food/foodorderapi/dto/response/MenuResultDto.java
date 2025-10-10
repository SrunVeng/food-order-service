package com.food.foodorderapi.dto.response;


import lombok.Data;

@Data
public class MenuResultDto {

    private Long id;
    private String name;
    private String description;
    private String basePrice;

}
