package com.food.foodorderapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.food.foodorderapi.dto.request.GroupCreateRequestDto;
import com.food.foodorderapi.dto.request.GroupDeleteRequestDto;
import com.food.foodorderapi.dto.request.GroupUpdateRequestDto;
import com.food.foodorderapi.dto.response.GroupResultDto;

public interface GroupService {

    void createGroup(GroupCreateRequestDto requestDto);

    void deleteGroup(GroupDeleteRequestDto requestDto);

    Page<GroupResultDto> findAll(Pageable pageable);

    void updateGroup(GroupUpdateRequestDto groupUpdateRequestDto);
}
