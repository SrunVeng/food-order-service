package com.food.foodorderapi.service;

import com.food.foodorderapi.dto.request.RestaurantCreateRequestDto;
import com.food.foodorderapi.dto.request.RestaurantDeleteRequestDto;
import com.food.foodorderapi.dto.response.RestaurantCreateResultDto;

public interface RestaurantService {
    RestaurantCreateResultDto create(RestaurantCreateRequestDto requestDto);

    void delete(RestaurantDeleteRequestDto requestDto);
}
