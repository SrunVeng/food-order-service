package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterVerifyRequestVo {

    @NotBlank(message = "first name cannot be blank")
    private String firstName;
    @NotBlank(message = "last name cannot be blank")
    private String lastName;
    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotBlank(message = "confirmPassword cannot be blank")
    private String confirmPassword;

    @NotBlank(message = "phone number cannot be blank")
    private String phoneNumber;

    @NotBlank(message = "email cannot be blank")
    @Email
    private String email;

    private String otp;
}
