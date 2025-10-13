package com.food.foodorderapi.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantMenuDeleteRequestDto {


    private Long restaurantId;
    private Long menuId;

}
