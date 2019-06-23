package com.crud.theatre.mapper;

import com.crud.theatre.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationMapperTest {

    @InjectMocks
    private ReservationMapper reservationMapper;

    @Test
    public void mapToReservationDtoList() {
        //Given
        User user = new User(1l, "firstName", "lastName", "test@test.com", "password");
        StageCopy stageCopy = StageCopy.builder().id(2l).build();
        Seats seats = new Seats(1l, 2, stageCopy, "RESERVERD");

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1l, LocalDateTime.now(), user, stageCopy, seats, seats.getNumber()));

        //when
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservations);

        //then
        ReservationDto reservationDto = reservationDtoList.get(0);

        assertNotEquals(reservationDtoList, reservations);
        assertNotNull(reservationDto.getUserId());
        assertNotNull(reservationDto.getReservationId());
        assertEquals(LocalDateTime.now().getDayOfYear(), reservationDto.getReservationDate().getDayOfYear());
        assertNotNull(reservationDto.getSeatsId());
        assertEquals(2, reservationDto.getSeatsNumber().intValue());
    }
}