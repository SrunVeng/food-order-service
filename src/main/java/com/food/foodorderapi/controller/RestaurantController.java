package com.food.foodorderapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import com.food.foodorderapi.dto.request.RestaurantMenuDeleteRequestDto;
import com.food.foodorderapi.dto.request.RestaurantMenuUpdateRequestDto;
import com.food.foodorderapi.dto.response.MenuResultDto;
import com.food.foodorderapi.dto.response.RestaurantResultDto;
import com.food.foodorderapi.library.messagebuilder.PageResponse;
import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;
import com.food.foodorderapi.mapper.MenuMapper;
import com.food.foodorderapi.mapper.RestaurantMapper;
import com.food.foodorderapi.service.RestaurantService;
import com.food.foodorderapi.vo.request.RestaurantMenuDeleteRequestVo;
import com.food.foodorderapi.vo.request.RestaurantMenuUpdateRequestVo;
import com.food.foodorderapi.vo.response.MenuResponseVo;
import com.food.foodorderapi.vo.response.RestaurantResponseVo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final MenuMapper menuMapper;


    @GetMapping("/get-all")
    public ResponseMessageBuilder.ResponseMessage<PageResponse<RestaurantResponseVo>> listAllUser(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<RestaurantResultDto> page = restaurantService.findAll(pageable);
        PageResponse<RestaurantResponseVo> payload = PageResponse.from(page.map(restaurantMapper::toRestaurantResponseVo));
        return new ResponseMessageBuilder<PageResponse<RestaurantResponseVo>>().success().addData(payload).build();
    }

    @PostMapping("/update/menu")
    public ResponseMessageBuilder.ResponseMessage<Void> createMenu(@Valid @RequestBody RestaurantMenuUpdateRequestVo request) {
        RestaurantMenuUpdateRequestDto requestDto = restaurantMapper.toRestaurantUpdateRequestDto(request);
        restaurantService.updatemenu(requestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }

    @DeleteMapping("/delete/menu")
    public ResponseMessageBuilder.ResponseMessage<Void> deleteMenu(@Valid @RequestBody RestaurantMenuDeleteRequestVo request) {
        RestaurantMenuDeleteRequestDto requestDto = restaurantMapper.toRestaurantMenuDeleteRequestDto(request);
        restaurantService.deletemenu(requestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }
    @GetMapping("/{id}/menus")
    public ResponseMessageBuilder.ResponseMessage<List<MenuResponseVo>> getMenus(@PathVariable Long id) {
        List<MenuResultDto> menus = restaurantService.getMenusOfRestaurant(id);
        List<MenuResponseVo> menuSlimResponseVo = menuMapper.toMenuResponseVoList(menus);
        return new ResponseMessageBuilder<List<MenuResponseVo>>().success().addData(menuSlimResponseVo).build();
    }



}
