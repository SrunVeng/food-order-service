package com.food.foodorderapi.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.foodorderapi.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
}
