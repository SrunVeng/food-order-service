package com.food.foodorderapi.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.food.foodorderapi.entity.EmailVerificationOTP;

public interface EmailVerificationOTPRepository extends JpaRepository<EmailVerificationOTP,Long> {

    @Query("""
      select o from EmailVerificationOTP o
      where o.email = :email and o.purpose = :purpose and o.status = 'OPEN'
      order by o.createdAt desc
    """)
    List<EmailVerificationOTP> findOpenByEmailAndPurpose(@Param("email") String email,
                                                         @Param("purpose") EmailVerificationOTP.Purpose purpose);

    boolean existsByEmailAndPurposeAndStatus(String email, EmailVerificationOTP.Purpose purpose,
                                             EmailVerificationOTP.Status status);


    @Modifying
    @Query("delete from EmailVerificationOTP e where e.status in :statuses")
    int deleteAllByStatuses(@Param("statuses") Collection<EmailVerificationOTP.Status> statuses);

}
