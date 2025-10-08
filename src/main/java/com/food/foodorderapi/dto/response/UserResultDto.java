
package com.food.foodorderapi.dto.response;


import lombok.Data;



@Data
public class UserResultDto {

    private String userNo;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String ChatId;

}
