package com.food.foodorderapi.dto.response;


import lombok.Data;

import java.util.List;

import com.food.foodorderapi.entity.Menu;

@Data
public class RestaurantResultDto {

    private String id;
    private String name;
    private String locationLink;
    private String ownerChatId;
    private String ownerName;
    private String ownerPhoneNumber;
    private List<Menu> menus;
}
