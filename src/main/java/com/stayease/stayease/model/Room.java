package com.stayease.stayease.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String type;
    private Double pricePerNight;
    private Integer capacity;
    private String description;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @OneToMany(mappedBy = "room") //one room has many reservations
    //JPA needs to know this to build the relationship

    private List<Reservation> reservations;

    public enum RoomStatus {
        AVAILABLE, OCCUPIED, MAINTENANCE
    }

}
