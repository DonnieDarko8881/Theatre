package com.crud.theatre.mapper;

import com.crud.theatre.domain.Actor;
import com.crud.theatre.domain.ActorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActorMapper {

    @Autowired
    private SpectacleMapper spectacleMapper;

    public Actor mapToActor(final ActorDto actorDto) {
        return new Actor(actorDto.getFirstName(), actorDto.getLastName());
    }

    public ActorDto mapToActorDto(final Actor actor) {
        return new ActorDto(
                actor.getId(),
                actor.getFirstName(),
                actor.getLastName());
    }

    public List<ActorDto> mapToActorDtoList(final List<Actor> actors) {
        return actors.stream()
                .map(actor -> new ActorDto(
                        actor.getId(),
                        actor.getFirstName(),
                        actor.getLastName()))
                .collect(Collectors.toList());
    }

}
