package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReservationDto {
    private Long reservationId;
    private LocalDateTime reservationDate;
    private Long userId;
    private Long stageCopyId;
    private Long seatsId;
    private Integer seatsNumber;

    public ReservationDto(Long userId, Long stageCopyId, Integer seatsNumber) {
        this.userId = userId;
        this.stageCopyId = stageCopyId;
        this.seatsNumber = seatsNumber;
    }
}
