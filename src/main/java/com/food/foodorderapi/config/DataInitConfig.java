package com.food.foodorderapi.config;


import com.food.foodorderapi.entity.User;
import com.food.foodorderapi.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final PasswordEncoderBean encoderBean;

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

        User superAdminUser = new User();
        superAdminUser.setUsername("super");
        superAdminUser.setPassword(encoderBean.passwordEncoder().encode("123"));
        List<Role> superAdminRole = new ArrayList<>();
        superAdminRole.add(superAdmin);
        superAdminUser.setRoles(superAdminRole);
        superAdminUser.setEmail("asd");
        superAdminUser.setIsAccountNonExpired(true);
        superAdminUser.setIsAccountNonLocked(true);
        superAdminUser.setIsCredentialsNonExpired(true);
        superAdminUser.setIsBlock(false);
        superAdminUser.setIsVerified(true);
        userRepository.save(superAdminUser);



    }
}
