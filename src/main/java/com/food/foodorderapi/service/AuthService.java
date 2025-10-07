package com.food.foodorderapi.service;

import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.UserLoginResultDto;



public interface AuthService {


    UserLoginResultDto userLogin(UserLoginRequestDto userLoginRequestDto);

    UserLoginResultDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);

    void userRegister(UserRegisterRequestDto userRegisterRequestDto)  ;

    void userResetPassword(UserResetPasswordRequestDto userResetPasswordRequestDto);

    void userRegisterVerify(UserRegisterVerifyRequestDto requestDto);

    void performPasswordReset(PerformPasswordResetRequestDto performPassDto);
}
