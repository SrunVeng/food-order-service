package com.food.foodorderapi.service;

import com.food.foodorderapi.dto.request.RestaurantCreateRequestDto;
import com.food.foodorderapi.dto.request.RestaurantDeleteRequestDto;
import com.food.foodorderapi.dto.response.RestaurantCreateResultDto;
import com.food.foodorderapi.dto.response.RestaurantResultDto;
import com.food.foodorderapi.dto.response.UserResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {
    RestaurantCreateResultDto create(RestaurantCreateRequestDto requestDto);

    void delete(RestaurantDeleteRequestDto requestDto);

    Page<RestaurantResultDto> findAll(Pageable pageable);
}
