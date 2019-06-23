package com.crud.theatre.Facade;

import com.crud.theatre.domain.*;
import com.crud.theatre.mapper.ActorMapper;
import com.crud.theatre.mapper.SpectacleMapper;
import com.crud.theatre.service.ActorService;
import com.crud.theatre.service.DateService;
import com.crud.theatre.service.SpectacleService;
import com.crud.theatre.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpectacleFacade {
    private final SpectacleMapper spectacleMapper;
    private final SpectacleService spectacleService;
    private final ActorService actorService;
    private final ActorMapper actorMapper;
    private final DateService dateService;
    private final StageService stageService;

    @Autowired
    public SpectacleFacade(SpectacleMapper spectacleMapper,
                           SpectacleService spectacleService,
                           ActorService actorService,
                           ActorMapper actorMapper,
                           DateService dateService,
                           StageService stageService) {
        this.spectacleMapper = spectacleMapper;
        this.spectacleService = spectacleService;
        this.actorService = actorService;
        this.actorMapper = actorMapper;
        this.dateService = dateService;
        this.stageService = stageService;
    }

    public void save(SpectacleDto spectacleDto) {
        spectacleService.save(spectacleMapper.mapToSpectacle(spectacleDto));
    }


    public List<SpectacleDto> findAll() {
        return spectacleMapper.mapToSpectacleDtoList(spectacleService.findAll());
    }

    public List<ActorDto> getCast(Long spectacleId) {
        Spectacle spectacle = spectacleService.findById(spectacleId);
        return actorMapper.mapToActorDtoList(spectacle.getCast());
    }

    public List<SpectacleDateDto> getDates(Long spectacleId) {
        Spectacle spectacle = spectacleService.findById(spectacleId);
        return spectacleMapper.mapToDateDtoList(spectacle.getSpectacleDates());
    }

    public void addActorToCast(long spectacleId, long actorId) {
        Spectacle spectacle = spectacleService.findById(spectacleId);
        Actor actor = actorService.findById(actorId);
        if (!spectacle.getCast().contains(actor)) {
            spectacle.getCast().add(actor);
            actor.getSpectacles().add(spectacle);
        }
        actorService.save(actor);
    }

    public void saveSpectacleDate(Long spectacleId,
                                  SpectacleDateDto dateDto) {
        Spectacle spectacle = spectacleService.findById(spectacleId);
        Stage stage = stageService.findById(spectacle.getStage().getId());
        SpectacleDate spectacleDate = new SpectacleDate(dateDto.getDate(), spectacle, stage);
        stage.getDates().add(spectacleDate);
        dateService.save(spectacleDate);
    }

    public void delete(long spectacleId) {
        Spectacle spectacle = spectacleService.findById(spectacleId);
        Stage stage = stageService.findById(spectacle.getStage().getId());
        stage.getSpectacles().remove(spectacle);
        stageService.save(stage);
        spectacleService.delete(spectacle);
    }
}
