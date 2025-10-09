package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;


@Data
public class GroupCreateRequestVo {

    @NotBlank(message = "group name cannot be blank")
    private String groupName;

    @NotNull(message = "restaurantId cannot be null")
    private Long restaurantId;

    // If you still want a link, keep it optional:
    private String gatherPlaceLink;

    @NotNull(message = "maxPeople cannot be null")
    @Min(value = 1, message = "maxPeople must be at least 1")
    @Max(value = 100, message = "maxPeople must be at most 100")
    private Integer maxPeople;

    // The party's start date/time (in UTC). UI will send ISO-8601.
    @NotNull(message = "eventStartAt cannot be null")
    @Future(message = "eventStartAt must be in the future")
    private Instant eventStartAt;

    // How long the party lasts. Backend will compute end time from this.
    @NotNull(message = "durationMinutes cannot be null")
    @Min(value = 15, message = "durationMinutes must be at least 15")
    @Max(value = 480, message = "durationMinutes must be at most 480")
    private Integer durationMinutes;

    // Optional details
    private String gatherPlaceDetails;
    private String remark;

    // If you want to accept the map pin from the UI:
    @NotNull(message = "meeting is required")
    private Meeting meeting;

    @Data
    public static class Meeting {
        @NotNull(message = "lat is required")
        private Double lat;
        @NotNull(message = "lng is required")
        private Double lng;
        private String label; // optional
    }

}
