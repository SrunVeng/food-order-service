package com.food.foodorderapi.dto.request;



import com.food.foodorderapi.vo.request.GroupCreateRequestVo;
import lombok.Data;

import java.time.Instant;


@Data
public class GroupCreateRequestDto {

    private String groupName;
    private Long restaurantId;
    private String gatherPlaceLink;
    private Integer maxPeople;
    private Instant eventStartAt;
    private Integer durationMinutes;
    private String gatherPlaceDetails;
    private String remark;

    private GroupCreateRequestVo.Meeting meeting;

    @Data
    public static class Meeting {
        private Double lat;
        private Double lng;
        private String label;
    }


}
