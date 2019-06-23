package com.crud.theatre.mapper;

import com.crud.theatre.domain.*;
import com.crud.theatre.exception.SpectacleNotFoundException;
import com.crud.theatre.service.StageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpectacleMapperTest {

    @InjectMocks
    private SpectacleMapper spectacleMapper;

    @Mock
    private StageService stageService;

    @Test
    public void mapToSpectacle() {
        //given
        Stage stage = new Stage(3l, "stage name", 10);
        SpectacleDto spectacleDto = new SpectacleDto(1l, "spectacle name", 3l);

        when(stageService.findById(spectacleDto.getStageId())).thenReturn(stage);

        //when
        Spectacle spectacle = spectacleMapper.mapToSpectacle(spectacleDto);

        //then
        assertEquals(0, spectacle.getCast().size());
        assertEquals(0, spectacle.getSpectacleDates().size());
        assertEquals("spectacle name", spectacle.getName());
        assertEquals(3l, spectacle.getStage().getId().longValue());
    }

    @Test
    public void mapToSpectacleDtoList() {
        //given
        Stage stage = new Stage(3l, "stage name", 10);
        Spectacle spectacle = new Spectacle(1l, "spectacle name", stage, new ArrayList<>(), new ArrayList<>());

        List<Spectacle> spectacles = new ArrayList<>();
        spectacles.add(spectacle);

        //when
        List<SpectacleDto> spectacleDtoList = spectacleMapper.mapToSpectacleDtoList(spectacles);
        SpectacleDto spectacleDto = spectacleDtoList.get(0);

        //then
        assertEquals(1l, spectacleDto.getId());
        assertEquals("spectacle name", spectacleDto.getName());
        assertEquals(3l, spectacleDto.getStageId().longValue());
    }

    @Test
    public void mapToSpectacleDtoSet() {
        //given
        Stage stage = new Stage(3l, "stage name", 10);
        Spectacle spectacle = new Spectacle(1l, "spectacle name", stage, new ArrayList<>(), new ArrayList<>());

        Set<Spectacle> spectacles = new HashSet<>();
        spectacles.add(spectacle);

        //when
        Set<SpectacleDto> spectacleDtoSet = spectacleMapper.mapToSpectacleDtoSet(spectacles);
        SpectacleDto spectacleDto = spectacleDtoSet.stream()
                .findFirst().orElseThrow(SpectacleNotFoundException::new);


        //then
        assertEquals(1l, spectacleDto.getId());
        assertEquals("spectacle name", spectacleDto.getName());
        assertEquals(3l, spectacleDto.getStageId().longValue());
    }

    @Test
    public void mapToDateDtoList() {
        //given
        Stage stage = new Stage(3l, "stage name", 10);
        Spectacle spectacle = new Spectacle(1l, "spectacle name", stage, new ArrayList<>(), new ArrayList<>());
        SpectacleDate spectacleDate = SpectacleDate.builder()
                .id(1l)
                .date(LocalDateTime.parse("2000-10-10T10:50"))
                .spectacle(spectacle)
                .stage(stage)
                .build();

        List<SpectacleDate> spectacleDates = new ArrayList<>();
        spectacleDates.add(spectacleDate);

        //when
        List<SpectacleDateDto> spectacleDateDtoList = spectacleMapper.mapToDateDtoList(spectacleDates);
        SpectacleDateDto spectacleDateDto = spectacleDateDtoList.get(0);
        //then

        assertEquals(1l, spectacleDateDto.getId());
        assertEquals(LocalDateTime.parse("2000-10-10T10:50"), spectacleDateDto.getDate());
        assertEquals(1,spectacleDateDtoList.size());
    }
}