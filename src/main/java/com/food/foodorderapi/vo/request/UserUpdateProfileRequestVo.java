package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdateProfileRequestVo {
    private String firstName;
    private String lastName;
    private String email;        // triggers OTP flow if different from current
    private String phoneNumber;
    @Pattern(regexp = "^[FM]$", message = "gender must be 'F' or 'M'")
    private String gender;
    private String dob;
}
