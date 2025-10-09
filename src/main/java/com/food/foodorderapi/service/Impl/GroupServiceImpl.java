package com.food.foodorderapi.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.food.foodorderapi.dto.request.GroupCreateRequestDto;
import com.food.foodorderapi.dto.request.GroupDeleteRequestDto;
import com.food.foodorderapi.dto.request.GroupUpdateRequestDto;
import com.food.foodorderapi.dto.response.GroupResultDto;
import com.food.foodorderapi.entity.Group;
import com.food.foodorderapi.entity.Restaurant;
import com.food.foodorderapi.library.constant.ErrorCode;
import com.food.foodorderapi.library.exception.BusinessException;
import com.food.foodorderapi.mapper.GroupMapper;
import com.food.foodorderapi.repository.GroupRepository;
import com.food.foodorderapi.repository.RestaurantRepository;
import com.food.foodorderapi.service.GroupService;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public void createGroup(GroupCreateRequestDto requestDto) {
        Restaurant restaurant = restaurantRepository.findByIdWithMenus(requestDto.getRestaurantId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.RESTAURANT_NOT_FOUND.getCode(),
                        ErrorCode.RESTAURANT_NOT_FOUND.getMessage()
                ));
        Group group = new Group();
        group.setGatherPlaceLink(requestDto.getGatherPlaceLink());
        group.setGatherPlaceDetails(requestDto.getGatherPlaceDetails());
        group.setGroupName(requestDto.getGroupName());
        group.setRestaurant(restaurant);
        group.setCreatedAt(Instant.now());
        group.setStatus(Group.Status.OPEN);
        groupRepository.save(group);
    }

    @Override
    public void updateGroup(GroupUpdateRequestDto requestDto) {

        Group group = groupRepository.findById(requestDto.getGroupId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.GROUP_NOT_FOUND.getCode(),
                        ErrorCode.GROUP_NOT_FOUND.getMessage()
                ));
        group.setGroupName(requestDto.getGroupName());
        group.setGatherPlaceDetails(requestDto.getGatherPlaceDetails());
        group.setGatherPlaceLink(requestDto.getGatherPlaceLink());
        Restaurant restaurant = restaurantRepository.findByIdWithMenus(requestDto.getRestaurantId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.RESTAURANT_NOT_FOUND.getCode(),
                        ErrorCode.RESTAURANT_NOT_FOUND.getMessage()
                ));
        group.setRestaurant(restaurant);
        groupRepository.save(group);
    }

    @Override
    public void deleteGroup(GroupDeleteRequestDto requestDto) {
        Group group = groupRepository.findById(requestDto.getGroupId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.GROUP_NOT_FOUND.getCode(),
                        ErrorCode.GROUP_NOT_FOUND.getMessage()
                ));

        groupRepository.delete(group);
    }

    @Override
    public Page<GroupResultDto> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable).map(groupMapper::toGroupResultDto);
    }

}
