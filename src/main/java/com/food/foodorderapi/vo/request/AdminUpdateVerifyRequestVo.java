package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminUpdateVerifyRequestVo {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;

    @NotBlank(message = "OTP must not be blank")
    private String otp;

}
