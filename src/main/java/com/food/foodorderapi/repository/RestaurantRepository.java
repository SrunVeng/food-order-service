package com.food.foodorderapi.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.food.foodorderapi.entity.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    @EntityGraph(attributePaths = "menus")
    @Query("select r from Restaurant r where r.id = :id")
    Optional<Restaurant> findByIdWithMenus(@Param("id") Long id);
}
