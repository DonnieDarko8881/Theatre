package com.crud.theatre.service;

import com.crud.theatre.domain.Spectacle;
import com.crud.theatre.exception.SpectacleNotFoundException;
import com.crud.theatre.repository.SpectacleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpectacleService {

    private final SpectacleRepository spectacleRepository;

    @Autowired
    public SpectacleService(SpectacleRepository spectacleRepository) {
        this.spectacleRepository = spectacleRepository;
    }

    public List<Spectacle> findAll(){
        return spectacleRepository.findAll();
    }

    public void save(Spectacle spectacle){
        spectacleRepository.save(spectacle);
    }

    public Spectacle findById(long spectacleId) throws SpectacleNotFoundException {
       return spectacleRepository.findById(spectacleId).orElseThrow(SpectacleNotFoundException::new);
    }
}
