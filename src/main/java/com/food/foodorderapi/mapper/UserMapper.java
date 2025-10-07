package com.food.foodorderapi.mapper;



import org.mapstruct.*;

import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.UserLoginResultDto;
import com.food.foodorderapi.vo.request.*;
import com.food.foodorderapi.vo.response.UserLoginResponseVo;

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
}
