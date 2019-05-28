package com.crud.theatre.service;

import com.crud.theatre.domain.Reservation;
import com.crud.theatre.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void save(Reservation reservation){
        reservationRepository.save(reservation);
    }
}
