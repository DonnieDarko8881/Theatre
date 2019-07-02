package com.crud.theatre.controller;

import com.crud.theatre.Facade.ActorFacade;
import com.crud.theatre.domain.ActorDto;
import com.crud.theatre.domain.SpectacleDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class ActorController {

    private final ActorFacade facade;

    public ActorController(ActorFacade facade) {
        this.facade = facade;
    }

    @GetMapping(value = "/actors")
    public List<ActorDto> findAll() {
        return facade.findAll();
    }

    @GetMapping(value = "/actors/{actorId}/spectacles")
    public List<SpectacleDto> getSpectacles(@PathVariable long actorId) {
        return facade.getSpectacles(actorId);
    }

    @PostMapping(value = "/actors")
    public void save(@RequestBody ActorDto actorDto) {
        facade.saveActor(actorDto);
    }

    @DeleteMapping(value = "/actors/{actorId}")
    public void delete(@PathVariable long actorId) {
        facade.deleteActor(actorId);
    }
}
