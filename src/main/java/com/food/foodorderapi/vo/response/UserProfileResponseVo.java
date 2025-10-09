package com.food.foodorderapi.vo.response;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.food.foodorderapi.entity.Role;


@Data
public class UserProfileResponseVo {

    private String userNo;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String email;
    private String phoneNumber;
    private String ChatId;
    private List<Role> roles;
}
