package com.crud.theatre.Facade;

import com.crud.theatre.clients.fixer.service.FixerService;
import com.crud.theatre.domain.*;
import com.crud.theatre.mapper.ReservationMapper;
import com.crud.theatre.service.ReservationService;
import com.crud.theatre.service.SeatsService;
import com.crud.theatre.service.StageCopyService;
import com.crud.theatre.service.UserService;
import com.crud.theatre.service.mail.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationFacadeTest {

    @InjectMocks
    private ReservationFacade facade;

    @Mock
    private StageCopyService stageCopyService;
    @Mock
    private UserService userService;
    @Mock
    private SeatsService seatsService;
    @Mock
    private ReservationService reservationService;
    @Mock
    private ReservationMapper reservationMapper;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private FixerService fixerService;

    @Test
    public void createReservation() {
        //Given
        ReservationDto reservationDto = new ReservationDto(1l, 2l, 3);

        Seats seats = new Seats(1l, 3, StageCopy.builder().id(2l).build(), Status.FREE.toString());
        Set<Seats> seatsSet = new HashSet<>();
        seatsSet.add(seats);

        StageCopy stageCopy = StageCopy.builder()
                .id(2l)
                .seats(seatsSet)
                .spectaclePricePLN(new BigDecimal(50))
                .build();

        User user = new User(4l, "name", "lastName", "test@test.com", "password");

        when(stageCopyService.findById(reservationDto.getStageCopyId())).thenReturn(stageCopy);
        when(userService.findById(reservationDto.getUserId())).thenReturn(user);
        //when
        facade.createReservation(reservationDto);

        //then
        System.out.println(seats.getStatus());
        verify(userService, times(1)).save(user);
        verify(seatsService, times(1)).save(any(Seats.class));
        verify(reservationService, times(1)).save(any());
        verify(simpleEmailService, times(1)).sendReservationMail(
                any(User.class), any(StageCopy.class), any(Seats.class), any(Mail.class));
    }

    @Test
    public void findAll() {
        //Given
        List<ReservationDto> mappedReservationList = new ArrayList<>();
        ReservationDto reservationDto = new ReservationDto(1l, LocalDateTime.parse("2019-06-15T19:00:07"),
                2l, 3l, 4l, 5);
        mappedReservationList.add(reservationDto);

        when(reservationMapper.mapToReservationDtoList(reservationService.findAll())).thenReturn(mappedReservationList);

        //when
        List<ReservationDto> reservationDtoList = facade.findAll();

        //then
        reservationDtoList.stream().forEach(reservation -> {
            assertEquals(1l, reservation.getReservationId());
            assertNotNull("2019-06-15T19:00:07", reservation.getReservationDate());
            assertEquals(2l, reservation.getUserId());
            assertEquals(3l, reservation.getStageCopyId());
            assertEquals(4l, reservation.getSeatsId());
            assertEquals(5, reservation.getSeatsNumber().intValue());
        });
    }


}