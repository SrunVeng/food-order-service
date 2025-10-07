package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PerformPasswordResetRequestVo {

    @NotBlank(message = "token cannot be blank")
    private String token;
    @NotBlank(message = "New Password be blank")
    private String newPassword;
    @NotBlank(message = "Confirm Password be blank")
    private String confirmPassword;

}
