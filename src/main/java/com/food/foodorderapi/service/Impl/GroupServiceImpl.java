package com.food.foodorderapi.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.food.foodorderapi.dto.request.GroupCreateRequestDto;
import com.food.foodorderapi.dto.request.GroupDeleteRequestDto;
import com.food.foodorderapi.dto.response.GroupResultDto;
import com.food.foodorderapi.mapper.GroupMapper;
import com.food.foodorderapi.repository.GroupRepository;
import com.food.foodorderapi.service.GroupService;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;

    @Override
    public void createGroup(GroupCreateRequestDto requestDto) {

    }

    @Override
    public void deleteGroup(GroupDeleteRequestDto requestDto) {

    }

    @Override
    public Page<GroupResultDto> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable).map(groupMapper::toGroupResultDto);
    }
}
