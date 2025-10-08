package com.food.foodorderapi.service.Impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.food.foodorderapi.dto.request.MenuCreateRequestDto;
import com.food.foodorderapi.dto.request.MenuDeleteRequestDto;
import com.food.foodorderapi.dto.response.MenuCreateResultDto;
import com.food.foodorderapi.repository.MenuRepository;
import com.food.foodorderapi.service.MenuService;


@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;


    @Override
    public MenuCreateResultDto create(MenuCreateRequestDto requestDto) {
        return null;
    }

    @Override
    public void delete(MenuDeleteRequestDto requestDto) {

    }

}
