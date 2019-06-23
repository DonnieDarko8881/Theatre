package com.crud.theatre.mapper;

import com.crud.theatre.domain.Actor;
import com.crud.theatre.domain.ActorDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class ActorMapperTest {

    @InjectMocks
    private ActorMapper actorMapper;

    @Test
    public void mapToActor() {
        //given
        ActorDto actorDto = new ActorDto(1l,"test firstName", "test lastName");

        //when
        Actor actor = actorMapper.mapToActor(actorDto);

        //then
        assertNotEquals(actor,actorDto);
        assertEquals("test firstName",actor.getFirstName());
        assertEquals("test lastName",actor.getLastName());
    }

    @Test
    public void mapToActorDtoList() {
        //Given
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor(1l,"test firstName", "test lastName", Collections.emptyList()));

        //when
        List<ActorDto> actorDtoList = actorMapper.mapToActorDtoList(actors);

        //then
        assertNotEquals(actors,actorDtoList);
        assertEquals(Optional.of(1l),Optional.of(actorDtoList.get(0).getId()));
        assertEquals("test firstName",actorDtoList.get(0).getFirstName());
        assertEquals("test lastName",actorDtoList.get(0).getLastName());
    }
    @Test
    public void mapToEmptyActorDtoList() {
        //Given
        List<Actor> actors = Collections.emptyList();

        //when
        List<ActorDto> actorDtoList = actorMapper.mapToActorDtoList(actors);

        //then
        assertEquals(0,actorDtoList.size());
    }
}