package com.food.foodorderapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.UserLoginResultDto;
import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;
import com.food.foodorderapi.mapper.UserMapper;
import com.food.foodorderapi.service.AuthService;
import com.food.foodorderapi.vo.request.*;
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

    @PostMapping("/user/register/verify")
    public ResponseMessageBuilder.ResponseMessage<Void> userRegisterVerify(@Valid @RequestBody UserRegisterVerifyRequestVo request)   {
        UserRegisterVerifyRequestDto requestDto = userMapper.toUserRegisterVerifyRequestDto(request);
        authService.userRegisterVerify(requestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }

    @PostMapping("/user/reset-password/request")
    public ResponseMessageBuilder.ResponseMessage<Void> userResetPassword(@Valid @RequestBody UserResetPasswordRequestVo request)   {
        UserResetPasswordRequestDto requestDto = userMapper.toUserResetPasswordRequestDto(request);
        authService.userResetPassword(requestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }

    @PostMapping("/user/reset-password/confirm")
    public ResponseMessageBuilder.ResponseMessage<Void> confirmPasswordReset(
            @Valid @RequestBody PerformPasswordResetRequestVo req) {
        PerformPasswordResetRequestDto performPassDto = userMapper.toPerformPassDto(req);
        authService.performPasswordReset(performPassDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }

    @PostMapping("/admin/set-password")
    public ResponseMessageBuilder.ResponseMessage<Void> adminSetPassword(
            @Valid @RequestBody AdminSetPasswordRequestVo req) {
        AdminSetPasswordRequestDto reqDto = userMapper.toAdminSetPasswordRequestDto(req);
        authService.adminSetPassword(reqDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }


}
