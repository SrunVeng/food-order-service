package com.food.foodorderapi.vo.response;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenuUpdateRequestVo {
    @NotNull(message = "id is required")
    private Long id;
    private String name;
    private String description;
    private String basePrice;
}
