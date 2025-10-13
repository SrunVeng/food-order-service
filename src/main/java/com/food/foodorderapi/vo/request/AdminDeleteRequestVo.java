package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminDeleteRequestVo {

    @NotNull(message = "id is required")
    private Long id;
}
