package com.food.foodorderapi.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "menus")
public class Menu {

    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;


}
