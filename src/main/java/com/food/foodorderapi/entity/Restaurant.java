package com.food.foodorderapi.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "restaurant")
public class Restaurant {

    @Id
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Menu> menus;



}
