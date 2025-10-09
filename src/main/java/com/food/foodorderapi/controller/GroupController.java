package com.food.foodorderapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.food.foodorderapi.dto.response.GroupResultDto;
import com.food.foodorderapi.library.messagebuilder.PageResponse;
import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;
import com.food.foodorderapi.mapper.GroupMapper;
import com.food.foodorderapi.service.GroupService;
import com.food.foodorderapi.vo.request.GroupCreateRequestVo;
import com.food.foodorderapi.vo.request.GroupDeleteRequestVo;
import com.food.foodorderapi.vo.request.GroupUpdateRequestVo;
import com.food.foodorderapi.vo.response.GroupResponseVo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupMapper groupMapper;
    private final GroupService groupService;


    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    @PostMapping("/create")
    public ResponseMessageBuilder.ResponseMessage<Void> create(@Valid @RequestBody GroupCreateRequestVo request) {
        groupService.createGroup(groupMapper.toGroupCreateRequestDto(request));
        return new ResponseMessageBuilder<Void>().success().build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    @PostMapping("/update")
    public ResponseMessageBuilder.ResponseMessage<Void> update(@Valid @RequestBody GroupUpdateRequestVo request) {
        groupService.updateGroup(groupMapper.toGroupUpdateRequestDto(request));
        return new ResponseMessageBuilder<Void>().success().build();
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    @DeleteMapping("/delete")
    public ResponseMessageBuilder.ResponseMessage<Void> delete(@Valid @RequestBody GroupDeleteRequestVo request) {
        groupService.deleteGroup(groupMapper.toGroupDeleteRequestDto(request));
        return new ResponseMessageBuilder<Void>().success().build();
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/get-all")
    public ResponseMessageBuilder.ResponseMessage<PageResponse<GroupResponseVo>> list(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<GroupResultDto> page = groupService.findAll(pageable);
        PageResponse<GroupResponseVo> payload = PageResponse.from(page.map(groupMapper::toGroupResponseVo));
        return new ResponseMessageBuilder<PageResponse<GroupResponseVo>>().success().addData(payload).build();
    }

}
