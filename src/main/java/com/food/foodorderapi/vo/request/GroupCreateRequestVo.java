package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class GroupCreateRequestVo {


    @NotBlank(message = "group name cannot be blank")
    private String groupName;

    @NotBlank(message = "restaurantId cannot be blank")
    private Long restaurantId;

    @NotBlank(message = "gatherPlaceLink cannot be blank")
    private String gatherPlaceLink;

    private String gatherPlaceDetails;

}
