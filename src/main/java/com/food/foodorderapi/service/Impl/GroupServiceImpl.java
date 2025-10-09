package com.food.foodorderapi.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

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

        int duration = requestDto.getDurationMinutes();
        Instant start = requestDto.getEventStartAt();
        Instant end   = start.plus(duration, ChronoUnit.MINUTES);
        Instant now   = Instant.now();

        // 3) Build entity
        Group group = new Group();
        group.setGroupName(requestDto.getGroupName());
        group.setRestaurant(restaurant);

        group.setGatherPlaceLink(requestDto.getGatherPlaceLink());
        group.setGatherPlaceDetails(requestDto.getGatherPlaceDetails());
        group.setRemark(requestDto.getRemark());

        // If you kept maxPeople as Integer in the entity (recommended):
        group.setMaxPeople(requestDto.getMaxPeople());
        // If your entity still has String maxPeople, use:
        // group.setMaxPeople(String.valueOf(requestDto.getMaxPeople()));

        // Meeting pin (optional but recommended)
        if (requestDto.getMeeting() != null) {
            Group.Meeting m = new Group.Meeting();
            m.setLat(requestDto.getMeeting().getLat());
            m.setLng(requestDto.getMeeting().getLng());
            m.setLabel(requestDto.getMeeting().getLabel());
            group.setMeeting(m);
        }

        // Timestamps
        group.setCreatedAt(now);
        group.setUpdatedAt(now);

        // Scheduling fields
        group.setEventStartAt(start);
        group.setDurationMinutes(duration);
        group.setEventEndAt(end);
        group.setRsvpCloseAt(start); // RSVPs close at start time (simple rule)

        // Status: OPEN until the eventEndAt passes (or use IN_PROGRESS if you added it)
        group.setStatus(now.isBefore(end) ? Group.Status.OPEN : Group.Status.ENDED);

        // 4) Persist
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
