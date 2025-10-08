package com.food.foodorderapi.repository;



import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.food.foodorderapi.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {


    @Modifying
    @Transactional
    @Query("DELETE FROM Group g WHERE g.status = 'ENDED'")
    void deleteAllEndedGroups();
}
