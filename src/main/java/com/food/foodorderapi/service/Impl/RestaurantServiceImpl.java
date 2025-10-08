package com.food.foodorderapi.service.Impl;

import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.RestaurantResultDto;
import com.food.foodorderapi.dto.response.UserResultDto;
import com.food.foodorderapi.entity.Menu;
import com.food.foodorderapi.entity.Restaurant;
import com.food.foodorderapi.library.constant.ErrorCode;
import com.food.foodorderapi.library.exception.BusinessException;
import com.food.foodorderapi.mapper.RestaurantMapper;
import com.food.foodorderapi.repository.MenuRepository;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.food.foodorderapi.dto.response.RestaurantCreateResultDto;
import com.food.foodorderapi.repository.RestaurantRepository;
import com.food.foodorderapi.service.RestaurantService;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
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

    @Override
    public void updatemenu(RestaurantMenuUpdateRequestDto requestDto) {
        Optional<Restaurant> byId = restaurantRepository.findById(requestDto.getRestaurantId());
        if(ObjectUtils.isEmpty(byId)) {
            throw new BusinessException(ErrorCode.RESTAURANT_NOT_FOUND.getCode(),ErrorCode.RESTAURANT_NOT_FOUND.getMessage());
        }
        Restaurant restaurant = byId.get();
        List<Menu> allById = menuRepository.findAllById(requestDto.getMenuIds());
        restaurant.setMenus(allById);
    }

    @Override
    public void deletemenu(RestaurantMenuDeleteRequestDto requestDto) {
        Restaurant restaurant = restaurantRepository.findByIdWithMenus(requestDto.getRestaurantId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.RESTAURANT_NOT_FOUND.getCode(),
                        ErrorCode.RESTAURANT_NOT_FOUND.getMessage()
                ));

        Set<Long> idsToRemove = new HashSet<>(requestDto.getMenuIds());
        // relies on Menu#getId and equals/hashCode by id
        restaurant.getMenus().removeIf(m -> idsToRemove.contains(m.getId()));

        // If Restaurant is the owning side, this save is enough.
        restaurantRepository.save(restaurant);
    }

    @Override
    public void updateRestaurant(RestaurantUpdateRequestDto requestDto) {
        Restaurant restaurant = restaurantRepository.findById(requestDto.getRestaurantId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.RESTAURANT_NOT_FOUND.getCode(),
                        ErrorCode.RESTAURANT_NOT_FOUND.getMessage()
                ));

        restaurant.setName(requestDto.getName());
        restaurant.setLocationLink(requestDto.getLocationLink());
        restaurant.setOwnerPhoneNumber(requestDto.getOwnerPhoneNumber());
        restaurant.setOwnerChatId(requestDto.getOwnerChatId());
        restaurant.setOwnerName(requestDto.getOwnerName());
        restaurantRepository.save(restaurant);
    }
}
