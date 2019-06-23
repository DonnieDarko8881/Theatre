package com.crud.theatre.Facade;

import com.crud.theatre.clients.fixer.service.FixerService;
import com.crud.theatre.domain.*;
import com.crud.theatre.exception.SeatsNotFoundException;
import com.crud.theatre.mapper.ReservationMapper;
import com.crud.theatre.service.ReservationService;
import com.crud.theatre.service.SeatsService;
import com.crud.theatre.service.StageCopyService;
import com.crud.theatre.service.UserService;
import com.crud.theatre.service.mail.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationFacade {
    private final StageCopyService stageCopyService;
    private final UserService userService;
    private final SeatsService seatsService;
    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;
    private final SimpleEmailService simpleEmailService;
    private final FixerService fixerService;

    @Autowired
    public ReservationFacade(StageCopyService stageCopyService,
                             UserService userService,
                             SeatsService seatsService,
                             ReservationService reservationService,
                             ReservationMapper reservationMapper,
                             SimpleEmailService simpleEmailService,
                             FixerService fixerService) {
        this.stageCopyService = stageCopyService;
        this.userService = userService;
        this.seatsService = seatsService;
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
        this.simpleEmailService = simpleEmailService;
        this.fixerService = fixerService;
    }

    public void createReservation(ReservationDto reservationDto) throws SeatsNotFoundException {
        StageCopy stageCopy = stageCopyService.findById(reservationDto.getStageCopyId());
        Seats seats = stageCopy.getSeats().stream()
                .filter(seat -> seat.getNumber() == reservationDto.getSeatsNumber())
                .findFirst().orElseThrow(SeatsNotFoundException::new);
        seats.setStatus(Status.RESERVED.toString());
        User user = userService.findById(reservationDto.getUserId());
        userService.save(user);
        seatsService.save(seats);
        reservationService.save(new Reservation(user, stageCopy, seats, seats.getNumber()));

        simpleEmailService.sendReservationMail(user,stageCopy.getSpectaclePricePLN());
    }

    public List<ReservationDto> findAll() {
        return reservationMapper.mapToReservationDtoList(reservationService.findAll());
    }
}
