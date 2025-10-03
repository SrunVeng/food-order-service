package com.food.foodorderapi.service;

import com.food.foodorderapi.dto.request.GroupCreateRequestDto;
import com.food.foodorderapi.dto.request.GroupDeleteRequestDto;

public interface GroupService {

    void createGroup(GroupCreateRequestDto requestDto);

    void deleteGroup(GroupDeleteRequestDto requestDto);
}
