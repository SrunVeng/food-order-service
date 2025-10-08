package com.food.foodorderapi.vo.response;


import lombok.Data;

@Data
public class MenuUpdateRequestVo {
    private Long id;
    private String name;
    private String description;
    private String basePrice;
}
