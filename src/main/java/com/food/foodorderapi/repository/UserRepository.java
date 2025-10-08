package com.food.foodorderapi.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.food.foodorderapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByusername(String username);
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
}
