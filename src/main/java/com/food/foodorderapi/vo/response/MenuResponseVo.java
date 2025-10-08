package com.food.foodorderapi.vo.response;


import com.food.foodorderapi.entity.Menu;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class MenuResponseVo {


    private String name;
    private String description;
    private String basePrice;
}
