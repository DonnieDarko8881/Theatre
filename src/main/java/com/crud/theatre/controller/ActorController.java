package com.crud.theatre.controller;

import com.crud.theatre.domain.Actor;
import com.crud.theatre.domain.ActorDto;
import com.crud.theatre.domain.SpectacleDto;
import com.crud.theatre.mapper.ActorMapper;
import com.crud.theatre.mapper.SpectacleMapper;
import com.crud.theatre.repository.ActorRepository;
import com.crud.theatre.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class ActorController {

    private final ActorMapper actorMapper;
    private final ActorService actorService;
    private final SpectacleMapper spectacleMapper;

    @Autowired
    public ActorController(ActorMapper actorMapper, ActorService actorService, SpectacleMapper spectacleMapper) {
        this.actorMapper = actorMapper;
        this.actorService = actorService;
        this.spectacleMapper = spectacleMapper;
    }

    @GetMapping(value = "/actors")
    public List<ActorDto> getActors(){
        return actorMapper.mapToActorDtoList(actorService.findAll());
    }

    @GetMapping(value = "/actors/{actorId}/spectacles")
    public List<SpectacleDto> getActors(@PathVariable("actorId") Long actorId){
        Actor actor = actorService.findById(actorId);
        return spectacleMapper.mapToSpectacleDtoList(actor.getSpectacles());
    }

    @PostMapping(value = "/actors")
    public void saveActor(@RequestBody ActorDto actorDto){
        actorService.save(actorMapper.mapToActor(actorDto));
    }


}
