package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RestaurantDeleteRequestVo {

    @NotBlank(message = "Restaurant ID cannot be Blank")
    private Long restaurantId;
}
