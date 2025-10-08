package com.food.foodorderapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.MenuCreateResultDto;
import com.food.foodorderapi.dto.response.RestaurantCreateResultDto;
import com.food.foodorderapi.dto.response.UserResultDto;
import com.food.foodorderapi.library.messagebuilder.PageResponse;
import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;
import com.food.foodorderapi.mapper.MenuMapper;
import com.food.foodorderapi.mapper.RestaurantMapper;
import com.food.foodorderapi.mapper.UserMapper;
import com.food.foodorderapi.service.AuthService;
import com.food.foodorderapi.service.MenuService;
import com.food.foodorderapi.service.RestaurantService;
import com.food.foodorderapi.vo.request.*;
import com.food.foodorderapi.vo.response.MenuCreateResponseVo;
import com.food.foodorderapi.vo.response.RestaurantCreateResponseVo;
import com.food.foodorderapi.vo.response.UserResponseVo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final RestaurantMapper restaurantMapper;
    private final MenuMapper menuMapper;
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final AuthService authService;
    private final UserMapper userMapper;

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN','SCOPE_ROLE_SUPERADMIN')")
    @PostMapping("/create/restaurants")
    public ResponseMessageBuilder.ResponseMessage<RestaurantCreateResponseVo> createRestaurants(@Valid @RequestBody RestaurantCreateRequestVo request) {
        RestaurantCreateRequestDto requestDto = restaurantMapper.toRestaurantCreateRequestDto(request);
        RestaurantCreateResultDto restaurantCreateResultDto = restaurantService.create(requestDto);
        RestaurantCreateResponseVo response = restaurantMapper.toRestaurantCreateResponseVo(restaurantCreateResultDto);
        return new ResponseMessageBuilder<RestaurantCreateResponseVo>().addData(response).success().build();
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN','SCOPE_ROLE_SUPERADMIN')")
    @PostMapping("/create/menus")
    public ResponseMessageBuilder.ResponseMessage<MenuCreateResponseVo> createMenu(@Valid @RequestBody MenuCreateRequestVo request) {
        MenuCreateRequestDto requestDto = menuMapper.toMenuCreateRequestDto(request);
        MenuCreateResultDto menuCreateResultDto = menuService.create(requestDto);
        MenuCreateResponseVo response = menuMapper.toMenuCreateResponseVo(menuCreateResultDto);
        return new ResponseMessageBuilder<MenuCreateResponseVo>().addData(response).success().build();
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN','SCOPE_ROLE_SUPERADMIN')")
    @DeleteMapping("/delete/restaurants")
    public ResponseMessageBuilder.ResponseMessage<Void> deleteRestaurants(@Valid @RequestBody RestaurantDeleteRequestVo request) {
        RestaurantDeleteRequestDto requestDto = restaurantMapper.toRestaurantDeleteRequestDto(request);
        restaurantService.delete(requestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN','SCOPE_ROLE_SUPERADMIN')")
    @DeleteMapping("/delete/menus")
    public ResponseMessageBuilder.ResponseMessage<Void> deleteRestaurants(@Valid @RequestBody MenuDeleteRequestVo request) {
        MenuDeleteRequestDto requestDto = menuMapper.toMenuDeleteRequestDto(request);
        menuService.delete(requestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }




    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN','SCOPE_ROLE_SUPERADMIN')")
    @GetMapping("/get-all-user")
    public ResponseMessageBuilder.ResponseMessage<PageResponse<UserResponseVo>> listAllUser(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<UserResultDto> page = authService.findAll(pageable);
        PageResponse<UserResponseVo> payload = PageResponse.from(page.map(userMapper::toUserResponseVo));
        return new ResponseMessageBuilder<PageResponse<UserResponseVo>>().success().addData(payload).build();
    }


}
