package com.food.foodorderapi.service.Impl;

import com.food.foodorderapi.dto.request.MenuUpdateRequestDto;
import com.food.foodorderapi.dto.response.MenuCreateResultDto;
import com.food.foodorderapi.dto.response.MenuResultDto;
import com.food.foodorderapi.entity.Menu;
import com.food.foodorderapi.entity.Restaurant;
import com.food.foodorderapi.library.constant.ErrorCode;
import com.food.foodorderapi.library.exception.BusinessException;
import com.food.foodorderapi.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.food.foodorderapi.dto.request.MenuCreateRequestDto;
import com.food.foodorderapi.dto.request.MenuDeleteRequestDto;
import com.food.foodorderapi.repository.MenuRepository;
import com.food.foodorderapi.service.MenuService;

import java.time.Instant;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;


    @Override
    public MenuCreateResultDto create(MenuCreateRequestDto requestDto) {
        Menu menu = new Menu();
        menu.setName(requestDto.getName());
        menu.setDescription(requestDto.getDescription());
        menu.setBasePrice(requestDto.getBasePrice());
        menu.setCreatedAt(Instant.now());
        menuRepository.save(menu);

        MenuCreateResultDto resultDto = new MenuCreateResultDto();
        resultDto.setName(menu.getName());
        resultDto.setDescription(menu.getDescription());
        resultDto.setBasePrice(menu.getBasePrice());
        return resultDto;
    }

    @Override
    public void delete(MenuDeleteRequestDto requestDto) {
        Optional<Menu> byId = menuRepository.findById(requestDto.getMenuId());
        if(ObjectUtils.isEmpty(byId)){
            throw new BusinessException(ErrorCode.MENU_NOT_FOUND.getCode(), ErrorCode.MENU_NOT_FOUND.getMessage());
        }
        Menu menu = byId.get();
        menuRepository.delete(menu);
    }

    @Override
    public Page<MenuResultDto> findAll(Pageable pageable) {
        return menuRepository.findAll(pageable).map(menuMapper::toMenuResultDto);
    }

    @Override
    public void updateMenu(MenuUpdateRequestDto requestDto) {
        Menu menu = menuRepository.findById(requestDto.getId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.MENU_NOT_FOUND.getCode(),
                        ErrorCode.MENU_NOT_FOUND.getMessage()
                ));

        menu.setName(requestDto.getName());
        menu.setDescription(requestDto.getDescription());
        menu.setBasePrice(requestDto.getBasePrice());
        menuRepository.save(menu);
    }
}
