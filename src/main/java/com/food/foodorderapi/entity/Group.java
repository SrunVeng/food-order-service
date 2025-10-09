package com.food.foodorderapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Data
@Table(
        name = "groups",
        indexes = {
                @Index(name = "idx_groups_start", columnList = "event_start_at"),
                @Index(name = "idx_groups_end", columnList = "event_end_at")
        }
)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)         // required
    private String groupName;

    // Optional: denormalized owner fields to match your UI (ownerId/ownerName)
    private Long ownerId;
    private String ownerName;

    @ManyToMany
    @JoinTable(
            name = "groups_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    // Many groups can reference one restaurant
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String gatherPlaceLink;
    private String gatherPlaceDetails;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "meeting_lat")),
            @AttributeOverride(name = "lng", column = @Column(name = "meeting_lng")),
            @AttributeOverride(name = "label", column = @Column(name = "meeting_label"))
    })
    private Meeting meeting;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    // numeric, not String
    @Column(nullable = false)
    private Integer maxPeople;

    private String remark;

    // --- Scheduling fields (all UTC) ---
    @Column(name = "event_start_at", nullable = false)
    private Instant eventStartAt;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    // You may persist eventEndAt for indexing & easy queries
    @Column(name = "event_end_at", nullable = false)
    private Instant eventEndAt;

    // Optional: when RSVPs close; simplest is at start time
    @Column(name = "rsvp_close_at", nullable = false)
    private Instant rsvpCloseAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public enum Status { OPEN, IN_PROGRESS, ENDED }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        updatedAt = now;

        // derive end/RSVP if not provided
        if (durationMinutes == null) durationMinutes = 90;
        if (eventStartAt == null) throw new IllegalStateException("eventStartAt is required");
        eventEndAt = eventStartAt.plus(durationMinutes, ChronoUnit.MINUTES);
        if (rsvpCloseAt == null) rsvpCloseAt = eventStartAt;

        // initial status from time
        updateStatusFromTime(now);
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
        // keep end time in sync if start/duration changed
        if (eventStartAt != null && durationMinutes != null) {
            eventEndAt = eventStartAt.plus(durationMinutes, ChronoUnit.MINUTES);
        }
        if (rsvpCloseAt == null) rsvpCloseAt = eventStartAt;
        updateStatusFromTime(updatedAt);
    }

    public boolean isEnded() {
        return Instant.now().isAfter(eventEndAt);
    }

    public void updateStatusFromTime(Instant ref) {
        if (ref.isBefore(eventStartAt)) {
            status = Status.OPEN;
        } else if (ref.isBefore(eventEndAt)) {
            status = Status.IN_PROGRESS;
        } else {
            status = Status.ENDED;
        }
    }

    @Embeddable
    @Data
    public static class Meeting {
        private Double lat;
        private Double lng;
        private String label;
    }
}
