package com.stayease.stayease.repository;

import com.stayease.stayease.model.Reservation;
import com.stayease.stayease.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user); //spring reads the method name and generates SQL automatically

    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId " +
            "AND r.status != 'CANCELLED' " +
            "AND r.checkIn < :checkOut " + //existing booking starts before your requested checkout
            "AND r.checkOut > :checkIn") //existing booking ends after your requested checkin
    List<Reservation> findOverlapping(@Param("roomId") Long roomId,
                                      @Param("checkIn") LocalDate checkIn,
                                      @Param("checkOut") LocalDate checkOut);
}
