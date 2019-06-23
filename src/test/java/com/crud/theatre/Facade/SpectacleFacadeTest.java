package com.crud.theatre.Facade;

import com.crud.theatre.domain.*;
import com.crud.theatre.mapper.ActorMapper;
import com.crud.theatre.mapper.SpectacleMapper;
import com.crud.theatre.service.ActorService;
import com.crud.theatre.service.DateService;
import com.crud.theatre.service.SpectacleService;
import com.crud.theatre.service.StageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpectacleFacadeTest {

    @InjectMocks
    private SpectacleFacade spectacleFacade;

    @Mock
    private SpectacleMapper spectacleMapper;
    @Mock
    private SpectacleService spectacleService;
    @Mock
    private ActorService actorService;
    @Mock
    private ActorMapper actorMapper;
    @Mock
    private DateService dateService;
    @Mock
    private StageService stageService;

    @Test
    public void shouldSaveSpectacle() {
        //give
        SpectacleDto spectacleDto = new SpectacleDto(1l, "firstName test", 2l);
        Spectacle spectacle = new Spectacle(1l, "firstName test",
                new Stage(2l, "stageName", 10), new ArrayList<>(), new ArrayList<>());

        when(spectacleMapper.mapToSpectacle(spectacleDto)).thenReturn(spectacle);
        //when
        spectacleFacade.save(spectacleDto);

        //then
        verify(spectacleService, times(1)).save(spectacle);
    }

    @Test
    public void fetchSpectacleDtoList() {
        //Given
        List<SpectacleDto> spectacleDtoList = new ArrayList<>();
        spectacleDtoList.add(new SpectacleDto(1l, "name test", 1l));

        when(spectacleMapper.mapToSpectacleDtoList(spectacleService.findAll())).thenReturn(spectacleDtoList);

        //when
        List<SpectacleDto> spectacles = spectacleFacade.findAll();

        //then
        spectacles.stream()
                .forEach(spectacleDto -> {
                            assertEquals(1l, spectacleDto.getId());
                            assertEquals("name test", spectacleDto.getName());
                            assertEquals(1l, spectacleDto.getStageId().longValue());
                        }
                );
        assertEquals(1, spectacles.size());
    }

    @Test
    public void fetchActorDtoList() {
        //Given
        List<ActorDto> castDto = new ArrayList<>();
        castDto.add(new ActorDto(1l, "firstName test", "lastName test"));
        List<Actor> cast = new ArrayList<>();
        cast.add(new Actor(1l, "firstName test", "lastName test", new ArrayList<>()));
        Stage stage = new Stage(2l, "stageName", 10);
        Spectacle spectacle = new Spectacle(2l, "stageName", stage, new ArrayList<>(), cast);

        when(spectacleService.findById(2l)).thenReturn(spectacle);
        when(actorMapper.mapToActorDtoList(spectacle.getCast())).thenReturn(castDto);

        //when
        List<ActorDto> actors = spectacleFacade.getCast(spectacle.getId());

        //then
        actors.stream().forEach(actorDto -> {
            assertEquals(1l, actorDto.getId().longValue());
            assertEquals("firstName test", actorDto.getFirstName());
            assertEquals("lastName test", actorDto.getLastName());
        });

    }

    @Test
    public void fetchSpectacleDateDtoList() {
        SpectacleDto spectacleDto = new SpectacleDto(1L, "Spectacle Test");
        StageDto stageDto = new StageDto(1l, "Stage Test", 10);
        LocalDateTime date = LocalDateTime.parse("2000-10-10T10:43");
        StageCopyDto stageCopyDto = StageCopyDto.builder().id(1l).build();
        List<SpectacleDateDto> spectacleDateDtoList = new ArrayList<>();
        spectacleDateDtoList.add(new SpectacleDateDto(1l, date, spectacleDto, stageDto, stageCopyDto));
        Stage stage = new Stage(2l, "stageName", 10);
        Spectacle spectacle = new Spectacle(1l, "Spectacle Test", stage, new ArrayList<>(), new ArrayList<>());

        when(spectacleService.findById(1l)).thenReturn(spectacle);
        when(spectacleMapper.mapToDateDtoList(spectacle.getSpectacleDates())).thenReturn(spectacleDateDtoList);

        //when
        List<SpectacleDateDto> spectacleDates = spectacleFacade.getDates(1l);

        //then
        spectacleDates.stream().forEach(spectacleDate -> {
            assertEquals(1l, spectacleDate.getId());
            assertEquals(LocalDateTime.parse("2000-10-10T10:43"), date);
            assertNotNull(spectacleDate.getSpectacleDto());
            assertNotNull(spectacleDate.getStageCopyDto());
            assertNotNull(spectacleDate.getStageDto());
        });
    }

    @Test
    public void addActorToCast() {
        //Given
        Stage stage = new Stage(2l, "stageName", 10);
        Spectacle spectacle = new Spectacle(1l,"test name",stage, new ArrayList<>(), new ArrayList<>());
        Actor actor = new Actor(3l,"firstName test", "lastName test", new ArrayList<>());

        when(spectacleService.findById(1l)).thenReturn(spectacle);
        when(actorService.findById(3l)).thenReturn(actor);

        //when
        spectacleFacade.addActorToCast(spectacle.getId(), actor.getId());

        //then
        verify(actorService, times(1)).save(actor);
        assertTrue(actor.getSpectacles().contains(spectacle));
        assertTrue(spectacle.getCast().contains(actor));
    }

    @Test
    public void shouldSaveSpectacleDate() {
        Stage stage = new Stage(2L, "stageName", 10);
        Spectacle spectacle = new Spectacle(1L,"test name",stage, new ArrayList<>(), new ArrayList<>());
        SpectacleDateDto spectacleDateDto = SpectacleDateDto.builder()
                .id(3L)
                .date(LocalDateTime.parse("2000-10-10T10:50"))
                .build();

        when(spectacleService.findById(1L)).thenReturn(spectacle);
        when(stageService.findById(spectacle.getStage().getId())).thenReturn(stage);

        //when
        spectacleFacade.saveSpectacleDate(1L,spectacleDateDto);

        //then
        verify(dateService,times(1)).save(any(SpectacleDate.class));
        assertEquals(1,stage.getDates().size());
    }

    @Test
    public void shouldDeleteSpectacle() {
        //given
        Stage stage = new Stage(2L, "stageName", 10);
        Spectacle spectacle = new Spectacle(1L,"test name",stage, new ArrayList<>(), new ArrayList<>());
        stage.getSpectacles().add(spectacle);

        when(spectacleService.findById(1L)).thenReturn(spectacle);
        when(stageService.findById(2L)).thenReturn(stage);

        //when
        spectacleFacade.delete(1L);

        //then
        verify(stageService, times(1)).save(stage);
        verify(spectacleService,times(1)).delete(spectacle);
        assertEquals(0,stage.getSpectacles().size());
    }
}