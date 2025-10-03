package com.food.foodorderapi.vo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequestVo {
    @NotBlank(message = "Refresh token is required")
    String refreshToken;
}
