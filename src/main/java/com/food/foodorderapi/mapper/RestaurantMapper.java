package com.food.foodorderapi.mapper;


import com.food.foodorderapi.dto.response.RestaurantResultDto;
import com.food.foodorderapi.entity.Restaurant;
import com.food.foodorderapi.vo.response.RestaurantResponseVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.food.foodorderapi.dto.request.RestaurantCreateRequestDto;
import com.food.foodorderapi.dto.request.RestaurantDeleteRequestDto;
import com.food.foodorderapi.dto.response.RestaurantCreateResultDto;
import com.food.foodorderapi.vo.request.RestaurantCreateRequestVo;
import com.food.foodorderapi.vo.request.RestaurantDeleteRequestVo;
import com.food.foodorderapi.vo.response.RestaurantCreateResponseVo;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    RestaurantCreateRequestDto toRestaurantCreateRequestDto(RestaurantCreateRequestVo reqVo);

    RestaurantCreateResponseVo toRestaurantCreateResponseVo(RestaurantCreateResultDto responseDto);

    RestaurantDeleteRequestDto toRestaurantDeleteRequestDto(RestaurantDeleteRequestVo request);

    RestaurantResponseVo toRestaurantResponseVo(RestaurantResultDto restaurant);

    RestaurantResultDto toRestaurantResultDto(Restaurant restaurant);
}
