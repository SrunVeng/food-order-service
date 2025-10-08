package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminCreateRequestVo {

    @NotBlank(message = "first name cannot be blank")
    private String firstName;
    @NotBlank(message = "last name cannot be blank")
    private String lastName;
    @NotBlank(message = "email cannot be blank")
    @Email
    private String email;
    @NotBlank(message = "phone number cannot be blank")
    private String phoneNumber;

}
