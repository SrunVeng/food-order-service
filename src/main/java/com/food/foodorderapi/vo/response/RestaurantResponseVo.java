package com.food.foodorderapi.vo.response;


import com.food.foodorderapi.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantResponseVo {

    private String name;
    private String locationLink;
    private String ownerPhoneNumber;
    private List<Menu> menus;
}
