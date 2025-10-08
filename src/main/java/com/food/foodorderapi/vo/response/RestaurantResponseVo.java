package com.food.foodorderapi.vo.response;


import lombok.Data;

import java.util.List;

import com.food.foodorderapi.entity.Menu;

@Data
public class RestaurantResponseVo {

    private String id;
    private String name;
    private String locationLink;
    private String ownerPhoneNumber;
    private List<Menu> menus;
}
