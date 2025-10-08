package com.food.foodorderapi.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column
    private String basePrice;

    private Instant createdAt;

    @ManyToMany
    @JoinTable(
            name = "restaurants_menus",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Restaurant> restaurants;


}
