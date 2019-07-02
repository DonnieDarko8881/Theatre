package com.crud.theatre.service;

import com.crud.theatre.domain.Seats;
import com.crud.theatre.repository.SeatsRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SeatsService {

    private final SeatsRepository seatsRepository;

    public SeatsService(SeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    public void save(Seats seats) {
        seatsRepository.save(seats);
    }

    public void deleteListSeats(Set<Seats> seats) {
        seatsRepository.deleteAll(seats);
    }
}
