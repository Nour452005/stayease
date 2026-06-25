package com.stayease.stayease.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //many reservations belong to one user, and to one room
    @JoinColumn(name = "user_id", nullable = false) //creates the foreign key column in the database
    private User user;

    @ManyToOne
    @JoinColumn(name= "room_id", nullable = false)
    private Room room;

    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public enum ReservationStatus{
        PENDING, CONFIRMED, CANCELLED, CHECKED_IN, CHECKED_OUT
    }
}
