package com.food.foodorderapi.dto.request;


import lombok.Data;

@Data
public class RestaurantCreateRequestDto {


    private String name;
    private String locationLink;
    private String ownerChatId;
    private String ownerName;
    private String ownerPhoneNumber;
}
