package com.food.foodorderapi.service;

import jakarta.validation.constraints.NotBlank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.AdminResultDto;
import com.food.foodorderapi.dto.response.UserLoginResultDto;
import com.food.foodorderapi.dto.response.UserProfileResultDto;
import com.food.foodorderapi.dto.response.UserResultDto;


public interface AuthService {


    UserLoginResultDto userLogin(UserLoginRequestDto userLoginRequestDto);

    UserLoginResultDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);

    void userRegister(UserRegisterRequestDto userRegisterRequestDto)  ;

    void userResetPassword(UserResetPasswordRequestDto userResetPasswordRequestDto);

    void userRegisterVerify(UserRegisterVerifyRequestDto requestDto);

    void performPasswordReset(PerformPasswordResetRequestDto performPassDto);

    void createAdmin(AdminCreateRequestDto requestDto);

    void deleteAdmin(AdminDeleteRequestDto requestDto);

    Page<UserResultDto> findAll(Pageable pageable);

    Page<AdminResultDto> findAllAdmin(Pageable pageable);

    void adminSetPassword (AdminSetPasswordRequestDto request);

    void updateAdmin(AdminUpdateRequestDto requestDto);

    UserProfileResultDto getbyUsername(@NotBlank String username);

    void deletebyUsername(String username);

    void updateByUsername(String username , UserUpdateProfileRequestDto req);

    void verifyUpdateEmail(String username, UserUpdateProfileVerifyRequestDto userUpdateProfileVerifyRequestDto);
}
