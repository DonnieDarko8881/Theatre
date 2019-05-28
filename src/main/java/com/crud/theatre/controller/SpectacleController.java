package com.crud.theatre.controller;

import com.crud.theatre.domain.*;
import com.crud.theatre.mapper.ActorMapper;
import com.crud.theatre.mapper.SpectacleMapper;
import com.crud.theatre.service.ActorService;
import com.crud.theatre.service.DateService;
import com.crud.theatre.service.SpectacleService;
import com.crud.theatre.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class SpectacleController {

    private final SpectacleMapper spectacleMapper;
    private final SpectacleService spectacleService;
    private final ActorService actorService;
    private final ActorMapper actorMapper;
    private final DateService dateService;
    private final StageService stageService;

    @Autowired
    public SpectacleController(SpectacleMapper spectacleMapper, SpectacleService spectacleService,
                               ActorService actorService, ActorMapper actorMapper,
                               DateService dateService, StageService stageService) {
        this.spectacleMapper = spectacleMapper;
        this.spectacleService = spectacleService;
        this.actorService = actorService;
        this.actorMapper = actorMapper;
        this.dateService = dateService;
        this.stageService = stageService;
    }

    @PostMapping(value = "/spectacles")
    public void saveSpectacle(@RequestBody SpectacleDto spectacleDto) {
        spectacleService.save(spectacleMapper.mapToSpectacle(spectacleDto));
    }

    @GetMapping(value = "/spectacles")
    public List<SpectacleDto> getSpectacles() {
        return spectacleMapper.mapToSpectacleDtoList(spectacleService.findAll());
    }

    @GetMapping(value = "/spectacles/{spectacleId}/cast")
    public List<ActorDto> getActors(@PathVariable("spectacleId") Long spectacleId) {
        Spectacle spectacle = spectacleService.findById(spectacleId);
        return actorMapper.mapToActorDtoList(spectacle.getCast());
    }

    @GetMapping(value = "/spectacles/{spectacleId}/dates")
    public List<SpectacleDateDto> getDates(@PathVariable("spectacleId") Long spectacleId) {
        Spectacle spectacle = spectacleService.findById(spectacleId);
        return spectacleMapper.mapToDateDtoList(spectacle.getSpectacleDates());
    }

    @PutMapping(value = "/spectacles/addActor")
    public void addActorToCast(@RequestParam("spectacleId") long spectacleId,
                               @RequestParam("actorId") long actorId) {
        Spectacle spectacle = spectacleService.findById(spectacleId);
        Actor actor = actorService.findById(actorId);
        if (!spectacle.getCast().contains(actor)) {
            spectacle.getCast().add(actor);
            actor.getSpectacles().add(spectacle);
            spectacleService.save(spectacle);
        }
    }

    @PostMapping(value = "/spectacles/{spectacleId}/dates")
    public void addSpectacleDate(@PathVariable("spectacleId") Long spectacleId,
                                 @RequestBody SpectacleDateDto dateDto) {
        Spectacle spectacle = spectacleService.findById(spectacleId);
        Stage stage = stageService.findById(spectacle.getStage().getId());
        SpectacleDate spectacleDate = new SpectacleDate(dateDto.getDate(), spectacle, stage);
        stage.getDates().add(spectacleDate);
        dateService.save(spectacleDate);
    }
}
