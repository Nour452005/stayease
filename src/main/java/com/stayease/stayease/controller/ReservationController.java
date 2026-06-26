package com.stayease.stayease.controller;

import com.stayease.stayease.dto.ReservationRequest;
import com.stayease.stayease.model.Reservation;
import com.stayease.stayease.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestBody ReservationRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.status(201)
                .body(reservationService.createReservation(request, email));
    }

    @GetMapping("/my")
    public ResponseEntity<List<Reservation>> getMyReservations(
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(reservationService.getMyReservations(email));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Reservation> cancelReservation(
            @PathVariable Long id,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(reservationService.cancelReservation(id, email));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reservation> updateStatus(
            @PathVariable Long id,
            @RequestParam Reservation.ReservationStatus status) {
        return ResponseEntity.ok(reservationService.updateStatus(id, status));
    }
}