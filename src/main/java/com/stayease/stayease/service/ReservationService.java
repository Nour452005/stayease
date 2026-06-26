package com.stayease.stayease.service;

import com.stayease.stayease.dto.ReservationRequest;
import com.stayease.stayease.model.Reservation;
import com.stayease.stayease.model.Room;
import com.stayease.stayease.model.User;
import com.stayease.stayease.repository.ReservationRepository;
import com.stayease.stayease.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final UserRepository userRepository;

    public Reservation createReservation(ReservationRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Room room = roomService.getRoomById(request.getRoomId());

        List<Reservation> overlapping = reservationRepository.findOverlapping(
                request.getRoomId(),
                request.getCheckIn(),
                request.getCheckOut()
        );

        if (!overlapping.isEmpty()) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        long nights = ChronoUnit.DAYS.between(request.getCheckIn(), request.getCheckOut());
        double totalPrice = nights * room.getPricePerNight();

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setCheckIn(request.getCheckIn());
        reservation.setCheckOut(request.getCheckOut());
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getMyReservations(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reservationRepository.findByUser(user);
    }

    public Reservation cancelReservation(Long id, String email) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (!reservation.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You can only cancel your own reservations");
        }

        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation updateStatus(Long id, Reservation.ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }
}
