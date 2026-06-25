package com.stayease.stayease.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservationRequest {
    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
