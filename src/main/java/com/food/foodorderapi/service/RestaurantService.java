package com.food.foodorderapi.service;

import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.RestaurantCreateResultDto;
import com.food.foodorderapi.dto.response.RestaurantResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {
    RestaurantCreateResultDto create(RestaurantCreateRequestDto requestDto);

    void delete(RestaurantDeleteRequestDto requestDto);

    Page<RestaurantResultDto> findAll(Pageable pageable);

    void updatemenu(RestaurantMenuUpdateRequestDto requestDto);

    void deletemenu(RestaurantMenuDeleteRequestDto requestDto);

    void updateRestaurant(RestaurantUpdateRequestDto requestDto);
}
