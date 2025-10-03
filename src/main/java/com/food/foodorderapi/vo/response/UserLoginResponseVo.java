package com.food.foodorderapi.vo.response;


import lombok.Data;

@Data
public class UserLoginResponseVo {

    String tokenType;
    String accessToken;
    String refreshToken;

}
