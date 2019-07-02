package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReservationDto {
    private long reservationId;
    private LocalDateTime reservationDate;
    private long userId;
    private long stageCopyId;
    private long seatsId;
    private Integer seatsNumber;

    public ReservationDto(long userId, long stageCopyId, Integer seatsNumber) {
        this.userId = userId;
        this.stageCopyId = stageCopyId;
        this.seatsNumber = seatsNumber;
    }
}
