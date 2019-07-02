package com.crud.theatre.service;

import com.crud.theatre.domain.Stage;
import com.crud.theatre.exception.StageNotFoundException;
import com.crud.theatre.repository.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StageService {

    private final StageRepository stageRepository;

    @Autowired
    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public Stage save(Stage stage) {
        return stageRepository.save(stage);
    }

    public Stage findById(long stageId) throws StageNotFoundException {
        return stageRepository.findById(stageId).orElseThrow(StageNotFoundException::new);
    }

    public List<Stage> findAll() {
        return stageRepository.findAll();
    }
}

