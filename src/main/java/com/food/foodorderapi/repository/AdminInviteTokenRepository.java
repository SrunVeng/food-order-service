package com.food.foodorderapi.repository;



import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.food.foodorderapi.entity.AdminInviteToken;

@Repository
public interface AdminInviteTokenRepository extends JpaRepository<AdminInviteToken, Long> {
    AdminInviteToken findByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM AdminInviteToken t WHERE t.status = 'USED'")
    void deleteAllUsedTokens();
}
