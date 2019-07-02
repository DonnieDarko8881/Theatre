package com.crud.theatre.Facade;

import com.crud.theatre.domain.*;
import com.crud.theatre.mapper.SpectacleDateMapper;
import com.crud.theatre.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpectacleDateFacade {

    private final SpectacleService spectacleService;
    private final StageService stageService;
    private final SeatsService seatsService;
    private final StageCopyService stageCopyService;
    private final DateService dateService;
    private final SpectacleDateMapper mapper;

    @Autowired
    public SpectacleDateFacade(SpectacleService spectacleService,
                               StageService stageService,
                               SeatsService seatsService,
                               StageCopyService stageCopyService,
                               DateService dateService,
                               SpectacleDateMapper mapper) {
        this.spectacleService = spectacleService;
        this.stageService = stageService;
        this.seatsService = seatsService;
        this.stageCopyService = stageCopyService;
        this.dateService = dateService;
        this.mapper = mapper;
    }

    public List<SpectacleDateDto> getSpectaclesDates() {
        return mapper.mapToSpectacleDateDtoList(dateService.findAll());
    }

    public void deleteSpectacleDate(long dateId) {
        SpectacleDate date = dateService.findById(dateId);
        Spectacle spectacle = spectacleService.findById(date.getSpectacle().getId());
        Stage stage = stageService.findById(date.getStage().getId());

        if (date.getStageCopy() != null) {
            StageCopy stageCopy = stageCopyService.findById(date.getStageCopy().getId());
            stageCopy.setSpectacleDate(null);
            stage.getStageCopies().remove(stageCopy);
            seatsService.deleteListSeats(stageCopy.getSeats());
            stageCopyService.deleteById(stageCopy.getId());
        }
        spectacle.getSpectacleDates().remove(date);
        stage.getDates().remove(date);
        dateService.deleteById(dateId);
    }
}
