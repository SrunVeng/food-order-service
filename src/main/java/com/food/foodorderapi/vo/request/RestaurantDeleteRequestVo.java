package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantDeleteRequestVo {

    @NotNull(message = "Restaurant ID cannot be Blank")
    private Long restaurantId;
}
