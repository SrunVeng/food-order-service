package com.food.foodorderapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.foodorderapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByusername(String username);
}
