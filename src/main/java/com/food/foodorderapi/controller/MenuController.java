package com.food.foodorderapi.controller;


import com.food.foodorderapi.dto.response.MenuResultDto;
import com.food.foodorderapi.dto.response.RestaurantResultDto;
import com.food.foodorderapi.library.messagebuilder.PageResponse;
import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;
import com.food.foodorderapi.mapper.MenuMapper;
import com.food.foodorderapi.service.MenuService;
import com.food.foodorderapi.vo.response.MenuResponseVo;
import com.food.foodorderapi.vo.response.RestaurantResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu")
public class MenuController {


    private final MenuService menuService;
    private final MenuMapper menuMapper;


    @GetMapping("/get-all")
    public ResponseMessageBuilder.ResponseMessage<PageResponse<MenuResponseVo>> listAllUser(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<MenuResultDto> page = menuService.findAll(pageable);
        PageResponse<MenuResponseVo> payload = PageResponse.from(page.map(menuMapper::toMenuResponseVo));
        return new ResponseMessageBuilder<PageResponse<MenuResponseVo>>().success().addData(payload).build();
    }


}
