package com.crud.theatre.service;

import com.crud.theatre.domain.Spectacle;
import com.crud.theatre.domain.SpectacleDate;
import com.crud.theatre.domain.Stage;
import com.crud.theatre.domain.StageCopy;
import com.crud.theatre.exception.DateNotFoundException;
import com.crud.theatre.repository.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DateService {
    private final DateRepository dateRepository;

    @Autowired
    public DateService(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    public SpectacleDate findById(long dateId){
        return dateRepository.findById(dateId).orElseThrow(DateNotFoundException::new);
    }

   public List<SpectacleDate> findAll(){
        return dateRepository.findAll();
   }

    public void save(SpectacleDate spectacleDate){
        dateRepository.save(spectacleDate);
    }

    public void deleteById(long dateId){
        dateRepository.deleteById(dateId);
    }
}
