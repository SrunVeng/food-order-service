package com.food.foodorderapi.repository;


import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.food.foodorderapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByusername(String username);
    User findByUserNo(String userNo);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);


    Optional<User> findByEmailIgnoreCase(String email);


    @Query(
            value = """
        select distinct u
        from User u
        join u.roles r
        where upper(r.name) in ('ADMIN','SUPERADMIN')
      """,
            countQuery = """
        select count(distinct u)
        from User u
        join u.roles r
        where upper(r.name) in ('ADMIN','SUPERADMIN')
      """
    )
    Page<User> findAllAdmin(Pageable pageable);

    @Modifying
    @Transactional
    @Query("""
        delete from User u
        where u.userNo = :userNo
          and exists (select r from u.roles r where upper(r.name) = 'ADMIN')
          and not exists (select r2 from u.roles r2 where upper(r2.name) = 'SUPERADMIN')
    """)
    void deleteAdminByUserNo(@Param("userNo") String userNo);

}
