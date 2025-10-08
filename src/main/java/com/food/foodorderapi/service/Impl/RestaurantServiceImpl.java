package com.food.foodorderapi.service.Impl;

import com.food.foodorderapi.dto.response.RestaurantResultDto;
import com.food.foodorderapi.dto.response.UserResultDto;
import com.food.foodorderapi.entity.Restaurant;
import com.food.foodorderapi.library.constant.ErrorCode;
import com.food.foodorderapi.library.exception.BusinessException;
import com.food.foodorderapi.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.food.foodorderapi.dto.request.RestaurantCreateRequestDto;
import com.food.foodorderapi.dto.request.RestaurantDeleteRequestDto;
import com.food.foodorderapi.dto.response.RestaurantCreateResultDto;
import com.food.foodorderapi.repository.RestaurantRepository;
import com.food.foodorderapi.service.RestaurantService;

import java.time.Instant;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public RestaurantCreateResultDto create(RestaurantCreateRequestDto requestDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(requestDto.getName());
        restaurant.setOwnerName(requestDto.getOwnerName());
        restaurant.setOwnerChatId(requestDto.getOwnerChatId());
        restaurant.setLocationLink(requestDto.getLocationLink());
        restaurant.setOwnerPhoneNumber(requestDto.getOwnerPhoneNumber());
        restaurant.setCreatedAt(Instant.now());
        restaurantRepository.save(restaurant);
        RestaurantCreateResultDto resultDto = new RestaurantCreateResultDto();
        resultDto.setName(restaurant.getName());
        resultDto.setOwnerName(restaurant.getOwnerName());
        resultDto.setOwnerChatId(restaurant.getOwnerChatId());
        resultDto.setLocationLink(restaurant.getLocationLink());
        resultDto.setOwnerPhoneNumber(restaurant.getOwnerPhoneNumber());
        return resultDto;
    }

    @Override
    public void delete(RestaurantDeleteRequestDto requestDto) {
        Optional<Restaurant> byId = restaurantRepository.findById(requestDto.getRestaurantId());
        if(ObjectUtils.isEmpty(byId)) {
            throw new BusinessException(ErrorCode.RESTAURANT_NOT_FOUND.getCode(),ErrorCode.RESTAURANT_NOT_FOUND.getMessage());
        }
        Restaurant restaurant = byId.get();
        restaurantRepository.delete(restaurant);
    }

    @Override
    public Page<RestaurantResultDto> findAll(Pageable pageable) {
        return restaurantRepository.findAll(pageable).map(restaurantMapper::toRestaurantResultDto);
    }
}
