package com.food.foodorderapi.dto.request;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RestaurantUpdateRequestDto {

    @NotBlank
    private Long restaurantId;

    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotBlank(message = "name cannot be blank")
    private String locationLink;
    @NotBlank(message = "Owner Chat Id cannot be blank")
    private String ownerChatId;

    @NotBlank(message = "Owner Phone Number cannot be blank")
    private String ownerPhoneNumber;

    private String ownerName;




}
