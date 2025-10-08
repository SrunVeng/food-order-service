package com.food.foodorderapi.mapper;



import jakarta.validation.Valid;

import org.mapstruct.*;

import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.AdminCreateResultDto;
import com.food.foodorderapi.dto.response.AdminResultDto;
import com.food.foodorderapi.dto.response.UserLoginResultDto;
import com.food.foodorderapi.dto.response.UserResultDto;
import com.food.foodorderapi.entity.User;
import com.food.foodorderapi.vo.request.*;
import com.food.foodorderapi.vo.response.AdminCreateResponseVo;
import com.food.foodorderapi.vo.response.AdminResponseVo;
import com.food.foodorderapi.vo.response.UserLoginResponseVo;
import com.food.foodorderapi.vo.response.UserResponseVo;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserLoginRequestDto toUserLoginRequestDto(UserLoginRequestVo requestVo);

    UserLoginResponseVo toUserLoginResponseVo(UserLoginResultDto resultDto);

    RefreshTokenRequestDto toRefreshTokenRequestDto(RefreshTokenRequestVo refreshTokenRequestVo);

    UserRegisterRequestDto toUserRegisterRequestDto(UserRegisterRequestVo requestVo);

    UserRegisterVerifyRequestDto toUserRegisterVerifyRequestDto(UserRegisterVerifyRequestVo requestVo);

    PerformPasswordResetRequestDto toPerformPassDto(PerformPasswordResetRequestVo requestVo);

    UserResetPasswordRequestDto toUserResetPasswordRequestDto(UserResetPasswordRequestVo request);

    AdminCreateRequestDto toAdminCreateRequestDto(AdminCreateRequestVo request);

    AdminCreateResponseVo toAdminCreateResponseVo(AdminCreateResultDto adminCreateResultDto);

    AdminDeleteRequestDto toAdminDeleteRequestDto(@Valid AdminDeleteRequestVo request);

    UserResponseVo toUserResponseVo(UserResultDto userResultDto);

    UserResultDto toUserResultDto(User user);

    AdminResponseVo toAdminResponseVo(AdminResultDto userResultDto);


    AdminResultDto toAdminResultDto(User user);
}
