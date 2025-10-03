package com.food.foodorderapi.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "groups")
public class Group {

    @Id
    private Long id;

    private List<User> users;

    @OneToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    private String gatherPlaceLink;

}
