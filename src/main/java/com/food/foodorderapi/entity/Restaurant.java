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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String locationLink;

    private Instant createdAt;

    private String ownerChatId;

    private String ownerName;

    private String ownerPhoneNumber;

    @ManyToMany(mappedBy = "restaurants")
    private List<Menu> menus;

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
    }

    public void addMenu(Menu m) {
        if (m == null) return;
        if (!menus.contains(m)) {
            menus.add(m);
        }
        if (!m.getRestaurants().contains(this)) {
            m.getRestaurants().add(this);
        }
    }

    public void removeMenu(Menu m) {
        if (m == null) return;
        menus.remove(m);
        m.getRestaurants().remove(this);
    }

}
