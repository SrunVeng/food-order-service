package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RestaurantMenuUpdateRequestVo {

    @NotNull(message = "restaurant id cannot be blank")
    private Long restaurantId;


    private Long menuId;

    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotBlank(message = "description cannot be blank")
    private String description;
    @NotBlank(message = "price cannot be blank")
    private String basePrice;

}
