package com.food.foodorderapi.dto.request;



import lombok.Data;

import java.time.Instant;


@Data
public class GroupCreateRequestDto {


    private String groupName;

    private Long restaurantId;

    private String gatherPlaceLink;

    private String gatherPlaceDetails;


}
