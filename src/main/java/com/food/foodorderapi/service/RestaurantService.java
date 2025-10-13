package com.food.foodorderapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.MenuResultDto;
import com.food.foodorderapi.dto.response.RestaurantCreateResultDto;
import com.food.foodorderapi.dto.response.RestaurantResultDto;

public interface RestaurantService {
    RestaurantCreateResultDto create(RestaurantCreateRequestDto requestDto);

    void delete(RestaurantDeleteRequestDto requestDto);

    Page<RestaurantResultDto> findAll(Pageable pageable);

    void updatemenu(RestaurantMenuUpdateRequestDto requestDto);

    void deletemenu(RestaurantMenuDeleteRequestDto requestDto);

    void updateRestaurant(RestaurantUpdateRequestDto requestDto);

    List<MenuResultDto> getMenusOfRestaurant(Long id);
}
