package com.crud.theatre.service;

import com.crud.theatre.domain.*;
import com.crud.theatre.exception.SeatsNotFoundException;
import com.crud.theatre.exception.StageCopyNotFoundException;
import com.crud.theatre.repository.StageCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StageCopyService {

    private final StageCopyRepository stageCopyRepository;
    private final StageService stageService;
    private final DateService dateService;

    @Autowired
    public StageCopyService(StageCopyRepository stageCopyRepository,
                            StageService stageService,
                            DateService dateService) {
        this.stageCopyRepository = stageCopyRepository;
        this.stageService = stageService;
        this.dateService = dateService;
    }

    public void save(StageCopy stageCopy) {
        stageCopyRepository.save(stageCopy);
    }

    public void delete(StageCopy stageCopy) {
        stageCopyRepository.delete(stageCopy);
    }

    public void deleteById(long copyId) {
        stageCopyRepository.deleteById(copyId);
    }

    public StageCopy findById(long stageCopyId) {
        return stageCopyRepository.findById(stageCopyId).orElseThrow(StageCopyNotFoundException::new);
    }

    public List<StageCopy> findAll() {
        return stageCopyRepository.findAll();
    }

    public void changeSeatsStatus(long stageCopyId, long seatsId, String status) {
        StageCopy stageCopy = findById(stageCopyId);
        Seats seats = stageCopy.getSeats().stream()
                .filter(seat -> seat.getId() == seatsId)
                .findFirst().orElseThrow(SeatsNotFoundException::new);
        seats.setStatus(status);
        save(stageCopy);
    }

    public void saveStageCopy(long stageId, long dateId, BigDecimal spectaclePrice) {
        Stage stage = stageService.findById(stageId);
        SpectacleDate spectacleDate = dateService.findById(dateId);
        if (spectacleDate.getStageCopy() == null) {
            Set<Seats> seats = new HashSet<>();
            StageCopy stageCopy = new StageCopy(stage, seats, spectacleDate, spectaclePrice);
            for (int i = 0; i < stage.getSeatsAmount(); i++) {
                seats.add(new Seats(i + 1, stageCopy, Status.FREE.toString()));
            }
            spectacleDate.setStageCopy(stageCopy);
            save(stageCopy);
        }
    }
}
