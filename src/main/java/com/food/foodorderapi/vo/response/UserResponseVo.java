package com.food.foodorderapi.vo.response;


import lombok.Data;

@Data
public class UserResponseVo {

    private String userNo;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String ChatId;
}
