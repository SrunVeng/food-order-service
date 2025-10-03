package com.food.foodorderapi.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.food.foodorderapi.dto.request.GroupCreateRequestDto;
import com.food.foodorderapi.dto.request.GroupDeleteRequestDto;
import com.food.foodorderapi.service.GroupService;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {


    @Override
    public void createGroup(GroupCreateRequestDto requestDto) {

    }

    @Override
    public void deleteGroup(GroupDeleteRequestDto requestDto) {

    }
}
