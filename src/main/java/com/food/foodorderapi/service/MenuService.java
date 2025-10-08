package com.food.foodorderapi.service;

import com.food.foodorderapi.dto.request.MenuCreateRequestDto;
import com.food.foodorderapi.dto.request.MenuDeleteRequestDto;
import com.food.foodorderapi.dto.response.MenuCreateResultDto;

public interface MenuService {
    MenuCreateResultDto create(MenuCreateRequestDto requestDto);

    void delete(MenuDeleteRequestDto requestDto);
}
