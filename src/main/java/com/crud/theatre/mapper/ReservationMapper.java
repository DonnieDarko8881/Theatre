package com.crud.theatre.mapper;

import com.crud.theatre.domain.Reservation;
import com.crud.theatre.domain.ReservationDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationMapper {
    public List<ReservationDto> mapToReservationDtoList(final List<Reservation> reservations){
        return reservations.stream()
                .map(reservation -> new ReservationDto(
                    reservation.getId(),
                    reservation.getReservationDate(),
                    reservation.getUser().getId(),
                    reservation.getStageCopy().getId(),
                    reservation.getSeats().getId(),
                    reservation.getSeatsNumber()
                ))
                .collect(Collectors.toList());
    }
}
