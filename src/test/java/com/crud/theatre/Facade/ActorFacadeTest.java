package com.crud.theatre.Facade;

import com.crud.theatre.domain.Actor;
import com.crud.theatre.domain.ActorDto;
import com.crud.theatre.domain.SpectacleDto;
import com.crud.theatre.mapper.ActorMapper;
import com.crud.theatre.mapper.SpectacleMapper;
import com.crud.theatre.service.ActorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActorFacadeTest {

    @InjectMocks
    private ActorFacade actorFacade;
    @Mock
    private ActorMapper actorMapper;
    @Mock
    private ActorService actorService;
    @Mock
    private SpectacleMapper spectacleMapper;

    @Test
    public void fetchActorDtoList() {
        //given
        List<Actor> actorList = new ArrayList<>();
        actorList.add(new Actor(1l, "firstName test", "lastName test", new ArrayList<>()));

        List<ActorDto> mappedActorList = new ArrayList<>();
        mappedActorList.add(new ActorDto(1l, "firstName test", "lastName test"));

        when(actorService.findAll()).thenReturn(actorList);
        when(actorMapper.mapToActorDtoList(actorList)).thenReturn(mappedActorList);

        //when
        List<ActorDto> actorDtoList = actorFacade.findAll();

        //then
        actorDtoList.stream().forEach(actorDto -> {
            assertEquals(1l, actorDto.getId());
            assertEquals("firstName test", actorDto.getFirstName());
            assertEquals("lastName test", actorDto.getLastName());
        });
    }

    @Test
    public void fetchSpectacleDtoList() {
        //given
        Actor actor = new Actor(1l, "firstName test", "lastName test", new ArrayList<>());

        List<SpectacleDto> mappedSpectacleList = new ArrayList<>();
        mappedSpectacleList.add(new SpectacleDto(1l, "name test", 1l));

        when(actorService.findById(anyLong())).thenReturn(actor);
        when(spectacleMapper.mapToSpectacleDtoList(actor.getSpectacles())).thenReturn(mappedSpectacleList);

        //when
        List<SpectacleDto> spectacleDtoList = actorFacade.getSpectacles(actor.getId());

        //then
        spectacleDtoList.stream().forEach(spectacleDto -> {
            assertEquals(1l, spectacleDto.getId());
            assertEquals("name test", spectacleDto.getName());
            assertEquals(1l, spectacleDto.getStageId().longValue());
        });
    }

    @Test
    public void shouldSaveActor() {
        //give
        ActorDto actorDto = new ActorDto(1l, "firstName test", "lastName test");
        Actor actor = new Actor("firstName test", "lastName test");

        when(actorMapper.mapToActor(actorDto)).thenReturn(actor);

        //when
        actorFacade.saveActor(actorDto);

        //then
        verify(actorService, times(1)).save(actor);
    }

    @Test
    public void shouldDeleteActor() {
        //when
        actorFacade.deleteActor(anyLong());
        //then
        verify(actorService, times(1)).delete(anyLong());
    }
}