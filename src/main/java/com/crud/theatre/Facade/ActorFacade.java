package com.crud.theatre.Facade;

import com.crud.theatre.domain.Actor;
import com.crud.theatre.domain.ActorDto;
import com.crud.theatre.domain.SpectacleDto;
import com.crud.theatre.mapper.ActorMapper;
import com.crud.theatre.mapper.SpectacleMapper;
import com.crud.theatre.service.ActorService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActorFacade {
    private final ActorMapper actorMapper;
    private final ActorService actorService;
    private final SpectacleMapper spectacleMapper;

    public ActorFacade(ActorMapper actorMapper,
                       ActorService actorService,
                       SpectacleMapper spectacleMapper) {
        this.actorMapper = actorMapper;
        this.actorService = actorService;
        this.spectacleMapper = spectacleMapper;
    }

    public List<ActorDto> findAll(){
        return actorMapper.mapToActorDtoList(actorService.findAll());
    }

    public List<SpectacleDto> getSpectacles(long actorId){
        Actor actor = actorService.findById(actorId);
        return spectacleMapper.mapToSpectacleDtoList(actor.getSpectacles());
    }

    public void saveActor(ActorDto actorDto){
        actorService.save(actorMapper.mapToActor(actorDto));
    }

    public void deleteActor(long actorId){
        actorService.delete(actorId);
    }
}

