package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupUpdateRequestVo {

    @NotBlank(message = "group id cannot be blank")
    private Long groupId;


    private String groupName;

    private Long restaurantId;

    private String gatherPlaceLink;

    private String gatherPlaceDetails;

    private String maxPeople;

    private String remark;

}
