package com.crud.theatre.mapper;

import com.crud.theatre.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpectacleDateMapperTest {

    @InjectMocks
    private SpectacleDateMapper spectacleDateMapper;

    @Mock
    private StageMapper stageMapper;

    @Before
    public void init(){
        StageDto stageDto = new StageDto(1l,"test stageName",10);
        when(stageMapper.mapToStageDto(any(Stage.class))).thenReturn(stageDto);
    }

    @Test
    public void mapToSpectacleDateDtoListWithNullStageCopy() {
        List<SpectacleDate> spectacleDateList = new ArrayList<>();
        spectacleDateList.add(getSpectacleDate(null));

        //when
        List<SpectacleDateDto> spectacleDateDtoList = spectacleDateMapper.mapToSpectacleDateDtoList(spectacleDateList);

        //then
        SpectacleDateDto spectacleDateDto = spectacleDateDtoList.get(0);

        assertEquals(null, spectacleDateDto.getStageCopyDto());
        assertEquals(LocalDateTime.parse("2000-10-10T16:50"), spectacleDateDto.getDate());
        assertEquals(1l, spectacleDateDto.getSpectacleDto().getId());
        assertEquals("test name", spectacleDateDto.getSpectacleDto().getName());
        assertEquals("test stageName", spectacleDateDto.getStageDto().getName());
    }

    @Test
    public void mapToSpectacleDateDtoListWithNotNullStageCopy() {
        List<SpectacleDate> spectacleDateList = new ArrayList<>();
        StageCopy stageCopy = StageCopy.builder().id(3l).build();
        spectacleDateList.add(getSpectacleDate(stageCopy));

        //when
        List<SpectacleDateDto> spectacleDateDtoList = spectacleDateMapper.mapToSpectacleDateDtoList(spectacleDateList);

        //then
        SpectacleDateDto spectacleDateDto = spectacleDateDtoList.get(0);

        assertEquals(3l, spectacleDateDto.getStageCopyDto().getId().longValue());
        assertEquals(LocalDateTime.parse("2000-10-10T16:50"), spectacleDateDto.getDate());
        assertEquals(1l, spectacleDateDto.getSpectacleDto().getId());
        assertEquals("test name", spectacleDateDto.getSpectacleDto().getName());
        assertEquals("test stageName", spectacleDateDto.getStageDto().getName());
    }

    @Test
    public void mapToSpectacleDateDto() {
        //Given
        StageCopy stageCopy = StageCopy.builder().id(3l).build();
        SpectacleDate spectacleDate = getSpectacleDate(stageCopy);

        //When
        SpectacleDateDto spectacleDateDto = spectacleDateMapper.mapToSpectacleDateDto(spectacleDate);

        //Then
        assertEquals(3l, spectacleDateDto.getStageCopyDto().getId().longValue());
        assertEquals(LocalDateTime.parse("2000-10-10T16:50"), spectacleDateDto.getDate());
        assertEquals(1l, spectacleDateDto.getSpectacleDto().getId());
        assertEquals("test name", spectacleDateDto.getSpectacleDto().getName());
        assertEquals("test stageName", spectacleDateDto.getStageDto().getName());

    }

    private SpectacleDate getSpectacleDate(StageCopy stageCopy){
        Stage stage = new Stage(1l,"test stageName",10);
        return SpectacleDate.builder()
                .id(1l)
                .date(LocalDateTime.parse("2000-10-10T16:50"))
                .spectacle(new Spectacle(1l,"test name",stage,new ArrayList<>(),new ArrayList<>()))
                .stage(stage)
                .stageCopy(stageCopy)
                .build();
    }
}