package com.food.foodorderapi.mapper;


import org.mapstruct.*;

import com.food.foodorderapi.dto.request.RefreshTokenRequestDto;
import com.food.foodorderapi.dto.request.UserLoginRequestDto;
import com.food.foodorderapi.dto.request.UserRegisterRequestDto;
import com.food.foodorderapi.dto.response.UserLoginResultDto;
import com.food.foodorderapi.vo.request.RefreshTokenRequestVo;
import com.food.foodorderapi.vo.request.UserLoginRequestVo;
import com.food.foodorderapi.vo.request.UserRegisterRequestVo;
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

}
