
package com.food.foodorderapi.vo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateProfileVerifyRequestVo {

    @Email
    private String email;

    @NotBlank(message = "OTP cannot be blank")
    private String otp;
}
