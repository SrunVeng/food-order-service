package com.food.foodorderapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.foodorderapi.entity.PasswordResetOTP;

public interface PasswordResetOTPRepository extends JpaRepository<PasswordResetOTP,Long> {
}
