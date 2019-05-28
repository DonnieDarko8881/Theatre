package com.crud.theatre.service;

import com.crud.theatre.domain.StageCopy;
import com.crud.theatre.exception.StageCopyNotFoundException;
import com.crud.theatre.repository.StageCopyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StageCopyService {

    private final StageCopyRepository stageCopyRepository;

    @Autowired
    public StageCopyService(StageCopyRepository stageCopyRepository) {
        this.stageCopyRepository = stageCopyRepository;
    }

    public void save(StageCopy stageCopy){
        stageCopyRepository.save(stageCopy);
    }

    public StageCopy findById(long stageCopyId){
        return stageCopyRepository.findById(stageCopyId).orElseThrow(StageCopyNotFoundException::new);
    }
}
