package com.food.foodorderapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;
import com.food.foodorderapi.mapper.GroupMapper;
import com.food.foodorderapi.service.GroupService;
import com.food.foodorderapi.vo.request.GroupCreateRequestVo;
import com.food.foodorderapi.vo.request.GroupDeleteRequestVo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupMapper groupMapper;
    private final GroupService groupService;

    @PostMapping("/create")
    public ResponseMessageBuilder.ResponseMessage<Void> create(@Valid @RequestBody GroupCreateRequestVo request) {
        groupService.createGroup(groupMapper.toGroupCreateRequestDto(request));
        return new ResponseMessageBuilder<Void>().success().build();
    }

    @DeleteMapping("/delete")
    public ResponseMessageBuilder.ResponseMessage<Void> delete(@Valid @RequestBody GroupDeleteRequestVo request) {
        groupService.deleteGroup(groupMapper.toGroupDeleteRequestDto(request));
        return new ResponseMessageBuilder<Void>().success().build();
    }

}
