package com.food.foodorderapi.vo.request;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserResetPasswordRequestVo {

    @NotBlank(message = "email cannot be blank")
    @Email
    private String email;
}
