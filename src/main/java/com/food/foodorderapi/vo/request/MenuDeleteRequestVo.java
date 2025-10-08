package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MenuDeleteRequestVo {

    @NotBlank(message = "menu id cannot be blank")
    private Long menuId;
}
