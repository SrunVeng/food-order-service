package com.food.foodorderapi.dto.request;



import lombok.Data;



@Data
public class GroupCreateRequestDto {


    private String groupName;

    private Long restaurantId;

    private String gatherPlaceLink;

    private String gatherPlaceDetails;


    private String maxPeople;

    private String remark;


}
