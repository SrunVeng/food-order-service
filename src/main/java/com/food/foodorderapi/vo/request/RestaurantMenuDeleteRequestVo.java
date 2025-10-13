package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantMenuDeleteRequestVo {

    @NotNull(message = "restaurant id cannot be blank")
    private Long restaurantId;

    @NotNull(message = "menuId cannot be blank")
    private Long menuId;

}
