package com.food.foodorderapi.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.foodorderapi.entity.AdminInviteToken;

@Repository
public interface AdminInviteTokenRepository extends JpaRepository<AdminInviteToken, Long> {
    AdminInviteToken findByToken(String token);
}
