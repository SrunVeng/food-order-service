package com.food.foodorderapi.service.Impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.food.foodorderapi.dto.request.RestaurantCreateRequestDto;
import com.food.foodorderapi.dto.request.RestaurantDeleteRequestDto;
import com.food.foodorderapi.dto.response.RestaurantCreateResultDto;
import com.food.foodorderapi.repository.RestaurantRepository;
import com.food.foodorderapi.service.RestaurantService;


@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantCreateResultDto create(RestaurantCreateRequestDto requestDto) {
        return null;
    }

    @Override
    public void delete(RestaurantDeleteRequestDto requestDto) {

    }
}
