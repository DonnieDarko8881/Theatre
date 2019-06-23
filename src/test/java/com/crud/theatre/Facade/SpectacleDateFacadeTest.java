package com.crud.theatre.Facade;

import com.crud.theatre.domain.*;
import com.crud.theatre.mapper.SpectacleDateMapper;
import com.crud.theatre.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpectacleDateFacadeTest {

    @InjectMocks
    private SpectacleDateFacade facade;

    @Mock
    private SpectacleService spectacleService;
    @Mock
    private StageService stageService;
    @Mock
    private SeatsService seatsService;
    @Mock
    private StageCopyService stageCopyService;
    @Mock
    private DateService dateService;
    @Mock
    private SpectacleDateMapper mapper;

    @Test
    public void fetchSpectacleDateDtoList() {
        //Given
        SpectacleDto spectacleDto = new SpectacleDto(1L, "Spectacle Test");
        StageDto stageDto = new StageDto(1L, "Stage Test", 10);
        LocalDateTime date = LocalDateTime.parse("2000-10-10T10:53");
        StageCopyDto stageCopyDto = StageCopyDto.builder().id(1l).build();

        List<SpectacleDateDto> spectacleDateDtoList = new ArrayList<>();
        spectacleDateDtoList.add(new SpectacleDateDto(1L, date, spectacleDto, stageDto, stageCopyDto));

        when(mapper.mapToSpectacleDateDtoList(dateService.findAll())).thenReturn(spectacleDateDtoList);

        //when
        List<SpectacleDateDto> spectaclesDates = facade.getSpectaclesDates();

        //then
        spectaclesDates.forEach(spectacleDateDto -> {
                    assertEquals(1, spectacleDateDto.getId());
                    assertEquals(LocalDateTime.parse("2000-10-10T10:53"), spectacleDateDto.getDate());
                    assertNotNull(spectacleDateDto.getStageDto());
                    assertNotNull(spectacleDateDto.getStageCopyDto());
                    assertNotNull(spectacleDateDto.getSpectacleDto());
                }
        );
    }

    @Test
    public void fetchSpectacleDateDtoListWithNullStageCopyDto() {
        //Given
        SpectacleDto spectacleDto = new SpectacleDto(1L, "Spectacle Test");
        StageDto stageDto = new StageDto(1L, "Stage Test", 10);
        LocalDateTime date = LocalDateTime.parse("2000-10-10T10:53");

        List<SpectacleDateDto> spectacleDateDtoList = new ArrayList<>();
        spectacleDateDtoList.add(new SpectacleDateDto(1L, date, spectacleDto, stageDto, null));

        when(mapper.mapToSpectacleDateDtoList(dateService.findAll())).thenReturn(spectacleDateDtoList);

        //when
        List<SpectacleDateDto> spectaclesDates = facade.getSpectaclesDates();

        //then
        spectaclesDates.forEach(spectacleDateDto -> {
                    assertEquals(1, spectacleDateDto.getId());
                    assertEquals(LocalDateTime.parse("2000-10-10T10:53"), spectacleDateDto.getDate());
                    assertNotNull(spectacleDateDto.getStageDto());
                    assertNull(spectacleDateDto.getStageCopyDto());
                    assertNotNull(spectacleDateDto.getSpectacleDto());
                }
        );
    }

    @Test
    public void shouldDeleteSpectacleDateWithNotNullStageCopy() {
        //given
        Stage stage = new Stage(3L, "stageName", 10);
        Spectacle spectacle = new Spectacle(2L, "name", stage, new ArrayList<>(), new ArrayList<>());
        SpectacleDate spectacleDate = SpectacleDate.builder()
                .id(1L)
                .date(LocalDateTime.parse("2000-10-10T13:50"))
                .stage(stage)
                .spectacle(spectacle)
                .build();

        StageCopy stageCopy = StageCopy.builder()
                .id(2L)
                .spectaclePricePLN(new BigDecimal(50))
                .seats(new HashSet<>())
                .spectacleDate(spectacleDate)
                .stage(stage)
                .build();

        spectacleDate.setStageCopy(stageCopy);
        stageCopy.getSeats().add(new Seats(1L,1,stageCopy,Status.FREE.toString()));
        spectacleDate.getSpectacle().getSpectacleDates().add(spectacleDate);
        spectacleDate.getStage().getDates().add(spectacleDate);

        when(stageCopyService.findById(anyLong())).thenReturn(stageCopy);
        when(dateService.findById(anyLong())).thenReturn(spectacleDate);
        when(spectacleService.findById(anyLong())).thenReturn(spectacle);
        when(stageService.findById(anyLong())).thenReturn(stage);

        //when
        facade.deleteSpectacleDate(1l);

        //then
        assertFalse(spectacle.getSpectacleDates().contains(spectacleDate));
        assertFalse(stage.getDates().contains(spectacleDate));
        assertFalse(stage.getStageCopies().contains(stageCopy));
        assertNull(stageCopy.getSpectacleDate());

        verify(dateService, times(1)).deleteById(1l);
        verify(seatsService,times(1)).deleteListSeats(stageCopy.getSeats());
        verify(stageCopyService,times(1)).deleteById(stageCopy.getId());
    }


    @Test
    public void shouldDeleteSpectacleDateWithNullStageCopy() {
        //given
        Stage stage = new Stage(3L, "stageName", 10);
        Spectacle spectacle = new Spectacle(2L, "name", stage, new ArrayList<>(), new ArrayList<>());
        SpectacleDate spectacleDate = SpectacleDate.builder()
                .id(1L)
                .date(LocalDateTime.parse("2000-10-10T13:50"))
                .stageCopy(null)
                .stage(stage)
                .spectacle(spectacle)
                .build();
        spectacleDate.getSpectacle().getSpectacleDates().add(spectacleDate);
        spectacleDate.getStage().getDates().add(spectacleDate);

        when(dateService.findById(anyLong())).thenReturn(spectacleDate);
        when(spectacleService.findById(anyLong())).thenReturn(spectacle);
        when(stageService.findById(anyLong())).thenReturn(stage);

        //when
        facade.deleteSpectacleDate(1l);

        //then
        assertFalse(spectacle.getSpectacleDates().contains(spectacleDate));
        assertFalse(stage.getDates().contains(spectacleDate));
        verify(dateService, times(1)).deleteById(1l);
    }
}