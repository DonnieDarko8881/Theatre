package com.crud.theatre.service;

import com.crud.theatre.domain.SpectacleDate;
import com.crud.theatre.exception.DateNotFoundException;
import com.crud.theatre.repository.DateRepository;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DateService {
    private final DateRepository dateRepository;

    @Autowired
    public DateService(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    public SpectacleDate findById(long dateId){
        return dateRepository.findById(dateId).orElseThrow(DateNotFoundException::new);
    }

    public void save(SpectacleDate spectacleDate){
        dateRepository.save(spectacleDate);
    }
}
