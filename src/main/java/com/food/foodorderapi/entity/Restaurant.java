package com.food.foodorderapi.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "restaurants")
public class Restaurant {

    @Id
    private Long id;

    private String name;

    private String locationLink;

    private Instant createdAt;

    private String ownerChatId;

    private String ownerName;

    private String ownerPhoneNumber;

    @ManyToMany(mappedBy = "restaurants")
    private List<Menu> menus;

}
