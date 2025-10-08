package com.food.foodorderapi.config;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.foodorderapi.entity.Role;
import com.food.foodorderapi.repository.RoleRepository;

@Component
@RequiredArgsConstructor
public class DataInitConfig {

    private final RoleRepository roleRepository;

    @PostConstruct
    @Transactional
    public void init() {
        Role user = new Role() ;
        user.setName("USER");
        Role admin = new Role() ;
        admin.setName("ADMIN");
        Role superAdmin = new Role() ;
        superAdmin.setName("SUPERADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(user);
        roles.add(admin);
        roles.add(superAdmin);
        roleRepository.saveAll(roles);
    }
}
