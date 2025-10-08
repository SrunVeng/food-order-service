package com.food.foodorderapi.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantMenuDeleteRequestDto {

    @NotBlank(message = "restaurant id cannot be blank")
    private Long restaurantId;

    @NotNull(message = "List of menuIds cannot be blank")
    private List<Long> menuIds;

}
