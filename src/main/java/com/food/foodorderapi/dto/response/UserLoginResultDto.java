package com.food.foodorderapi.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResultDto {

    String tokenType;
    String accessToken;
    String refreshToken;
}
