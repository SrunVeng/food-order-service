package com.food.foodorderapi.controller;


import com.food.foodorderapi.dto.response.RestaurantResultDto;
import com.food.foodorderapi.dto.response.UserResultDto;
import com.food.foodorderapi.library.messagebuilder.PageResponse;
import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;
import com.food.foodorderapi.mapper.RestaurantMapper;
import com.food.foodorderapi.service.RestaurantService;
import com.food.foodorderapi.vo.response.RestaurantResponseVo;
import com.food.foodorderapi.vo.response.UserResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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





}
