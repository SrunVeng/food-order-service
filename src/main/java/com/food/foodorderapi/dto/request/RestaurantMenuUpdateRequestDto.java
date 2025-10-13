package com.food.foodorderapi.dto.request;



import lombok.Data;


@Data
public class RestaurantMenuUpdateRequestDto {


    private Long restaurantId;
    private Long menuId;
    private String name;
    private String description;
    private String basePrice;
}
