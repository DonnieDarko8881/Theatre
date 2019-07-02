package com.crud.theatre.service;

import com.crud.theatre.domain.Reservation;
import com.crud.theatre.exception.ReservationNotFoundException;
import com.crud.theatre.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(ReservationNotFoundException::new);
    }
}
