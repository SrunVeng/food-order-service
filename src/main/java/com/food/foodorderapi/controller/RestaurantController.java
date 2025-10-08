package com.food.foodorderapi.controller;


import com.food.foodorderapi.dto.request.MenuCreateRequestDto;
import com.food.foodorderapi.dto.request.RestaurantMenuDeleteRequestDto;
import com.food.foodorderapi.dto.request.RestaurantMenuUpdateRequestDto;
import com.food.foodorderapi.dto.response.MenuCreateResultDto;
import com.food.foodorderapi.dto.response.RestaurantResultDto;
import com.food.foodorderapi.dto.response.UserResultDto;
import com.food.foodorderapi.library.messagebuilder.PageResponse;
import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;
import com.food.foodorderapi.mapper.RestaurantMapper;
import com.food.foodorderapi.service.RestaurantService;
import com.food.foodorderapi.vo.request.MenuCreateRequestVo;
import com.food.foodorderapi.vo.request.RestaurantMenuDeleteRequestVo;
import com.food.foodorderapi.vo.request.RestaurantMenuUpdateRequestVo;
import com.food.foodorderapi.vo.response.MenuCreateResponseVo;
import com.food.foodorderapi.vo.response.RestaurantResponseVo;
import com.food.foodorderapi.vo.response.UserResponseVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;



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

    @PostMapping("/delete/menu")
    public ResponseMessageBuilder.ResponseMessage<Void> deleteMenu(@Valid @RequestBody RestaurantMenuDeleteRequestVo request) {
        RestaurantMenuDeleteRequestDto requestDto = restaurantMapper.toRestaurantMenuDeleteRequestDto(request);
        restaurantService.deletemenu(requestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }




}
