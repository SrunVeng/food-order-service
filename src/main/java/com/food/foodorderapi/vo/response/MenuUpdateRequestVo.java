package com.food.foodorderapi.vo.response;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class MenuUpdateRequestVo {
    private Long id;
    private String name;
    private String description;
    private String basePrice;
}
