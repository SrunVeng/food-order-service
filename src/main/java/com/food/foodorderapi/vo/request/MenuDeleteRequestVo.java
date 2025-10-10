package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MenuDeleteRequestVo {

    @NotNull(message = "menu id is required")
    @Positive(message = "menu id must be positive")
    private Long menuId;
}
