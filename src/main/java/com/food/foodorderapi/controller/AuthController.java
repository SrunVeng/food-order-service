package com.food.foodorderapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import com.food.foodorderapi.dto.request.RefreshTokenRequestDto;
import com.food.foodorderapi.dto.request.UserLoginRequestDto;
import com.food.foodorderapi.dto.request.UserRegisterRequestDto;
import com.food.foodorderapi.dto.request.UserResetPasswordRequestDto;
import com.food.foodorderapi.dto.response.UserLoginResultDto;
import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;
import com.food.foodorderapi.mapper.UserMapper;
import com.food.foodorderapi.service.AuthService;
import com.food.foodorderapi.vo.request.RefreshTokenRequestVo;
import com.food.foodorderapi.vo.request.UserLoginRequestVo;
import com.food.foodorderapi.vo.request.UserRegisterRequestVo;
import com.food.foodorderapi.vo.request.UserResetPasswordRequestVo;
import com.food.foodorderapi.vo.response.UserLoginResponseVo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;


    @PostMapping("/user/login")
    public ResponseMessageBuilder.ResponseMessage<UserLoginResponseVo> login(@Valid @RequestBody UserLoginRequestVo request) {
        UserLoginRequestDto userLoginRequestDto = userMapper.toUserLoginRequestDto(request);
        UserLoginResultDto result = authService.userLogin(userLoginRequestDto);
        UserLoginResponseVo response = userMapper.toUserLoginResponseVo(result);
        return new ResponseMessageBuilder<UserLoginResponseVo>().success().addData(response).build();
    }


    @PostMapping("/user/login/refresh-token")
    public ResponseMessageBuilder.ResponseMessage<UserLoginResponseVo> refreshToken(@Valid @RequestBody RefreshTokenRequestVo request) {
        RefreshTokenRequestDto refreshRequestDto = userMapper.toRefreshTokenRequestDto(request);
        UserLoginResultDto result = authService.refreshToken(refreshRequestDto);
        UserLoginResponseVo response = userMapper.toUserLoginResponseVo(result);
        return new ResponseMessageBuilder<UserLoginResponseVo>().success().addData(response).build();
    }

    @PostMapping("/user/register")
    public ResponseMessageBuilder.ResponseMessage<Void> userRegister(@Valid @RequestBody UserRegisterRequestVo request)   {
        UserRegisterRequestDto requestDto = userMapper.toUserRegisterRequestDto(request);
        authService.userRegister(requestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }

    @PostMapping("/user/reset-password")
    public ResponseMessageBuilder.ResponseMessage<Void> userResetPassword(@Valid @RequestBody UserResetPasswordRequestVo request)   {
        UserResetPasswordRequestDto requestDto = userMapper.toUserResetPasswordRequestDto(request);
        authService.userResetPassword(requestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }


}
