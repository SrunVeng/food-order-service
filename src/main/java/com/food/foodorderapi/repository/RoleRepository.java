package com.food.foodorderapi.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.foodorderapi.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String admin);
}
