package com.food.foodorderapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.food.foodorderapi.dto.request.MenuCreateRequestDto;
import com.food.foodorderapi.dto.request.MenuDeleteRequestDto;
import com.food.foodorderapi.dto.request.MenuUpdateRequestDto;
import com.food.foodorderapi.dto.response.MenuCreateResultDto;
import com.food.foodorderapi.dto.response.MenuResultDto;

public interface MenuService {
    MenuCreateResultDto create(MenuCreateRequestDto requestDto);

    void delete(MenuDeleteRequestDto requestDto);

    Page<MenuResultDto> findAll(Pageable pageable);

    void updateMenu(MenuUpdateRequestDto requestDto);
}
