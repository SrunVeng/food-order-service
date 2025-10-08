package com.food.foodorderapi.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "groups")
public class Group {

    @Id
    private Long id;

    private String groupName;

    @ManyToMany
    @JoinTable(
            name = "groups_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id", unique = true)
    private Restaurant restaurant;

    private String gatherPlaceLink;

    private String gatherPlaceDetails;

    private Instant createdAt;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    public enum Status { OPEN, ENDED }

}
