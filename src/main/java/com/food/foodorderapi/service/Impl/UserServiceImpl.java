package com.food.foodorderapi.service.Impl;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.food.foodorderapi.repository.UserRepository;
import com.food.foodorderapi.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;






}
