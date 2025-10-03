package com.food.foodorderapi.service;

import com.food.foodorderapi.dto.request.RefreshTokenRequestDto;
import com.food.foodorderapi.dto.request.UserLoginRequestDto;
import com.food.foodorderapi.dto.request.UserRegisterRequestDto;
import com.food.foodorderapi.dto.response.UserLoginResultDto;

public interface AuthService {


    UserLoginResultDto userLogin(UserLoginRequestDto userLoginRequestDto);

    UserLoginResultDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);

    void userRegister(UserRegisterRequestDto userRegisterRequestDto);

}
