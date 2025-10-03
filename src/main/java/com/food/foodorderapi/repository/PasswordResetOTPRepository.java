package com.food.foodorderapi.repository;

import com.food.foodorderapi.entity.PasswordResetOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetOTPRepository extends JpaRepository<PasswordResetOTP,Long> {
}
