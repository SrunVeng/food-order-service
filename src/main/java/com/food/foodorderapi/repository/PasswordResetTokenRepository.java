package com.food.foodorderapi.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.food.foodorderapi.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    @Modifying
    @Query("update PasswordResetToken t set t.status = 'EXPIRED' where t.status = 'OPEN' and t.expiresAt < :now")
    void expireAllOlderThan(@Param("now") Instant now);

    @Modifying
    @Query("delete from PasswordResetToken t where t.status in ('USED','EXPIRED')")
    void deleteAllFinished();
}
