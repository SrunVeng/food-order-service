package com.food.foodorderapi.dto.request;



import lombok.Data;

import java.util.List;

@Data
public class RestaurantMenuUpdateRequestDto {


    private Long restaurantId;
    private List<Long> menuIds;
}
