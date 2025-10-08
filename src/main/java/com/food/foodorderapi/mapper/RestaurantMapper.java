package com.food.foodorderapi.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.RestaurantCreateResultDto;
import com.food.foodorderapi.dto.response.RestaurantResultDto;
import com.food.foodorderapi.entity.Restaurant;
import com.food.foodorderapi.vo.request.*;
import com.food.foodorderapi.vo.response.RestaurantCreateResponseVo;
import com.food.foodorderapi.vo.response.RestaurantResponseVo;

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

    RestaurantMenuUpdateRequestDto toRestaurantUpdateRequestDto(RestaurantMenuUpdateRequestVo request);

    RestaurantMenuDeleteRequestDto toRestaurantMenuDeleteRequestDto(RestaurantMenuDeleteRequestVo request);

    RestaurantUpdateRequestDto toRestaurantUpdateRequestDto(RestaurantUpdateRequestVo request);
}
