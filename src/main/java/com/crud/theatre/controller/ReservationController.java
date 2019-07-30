package com.crud.theatre.controller;

import com.crud.theatre.Facade.ReservationFacade;
import com.crud.theatre.domain.*;
import com.crud.theatre.exception.SeatsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class ReservationController {

    private final ReservationFacade reservationFacade;

    @Autowired
    public ReservationController(ReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    @PostMapping(value = "/reservations", consumes = APPLICATION_JSON_VALUE)
    public void createReservation(@RequestBody ReservationDto reservationDto) throws SeatsNotFoundException {
        reservationFacade.createReservation(reservationDto);
    }

    @GetMapping(value = "/reservations")
    public List<ReservationDto> findAll() {
        return reservationFacade.findAll();
    }
}
