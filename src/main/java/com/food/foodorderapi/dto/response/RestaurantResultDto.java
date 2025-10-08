package com.food.foodorderapi.dto.response;


import com.food.foodorderapi.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantResultDto {

    private String name;
    private String locationLink;
    private String ownerPhoneNumber;
    private List<Menu> menus;
}
