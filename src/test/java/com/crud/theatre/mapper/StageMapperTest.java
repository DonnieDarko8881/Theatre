package com.crud.theatre.mapper;

import com.crud.theatre.domain.Stage;
import com.crud.theatre.domain.StageDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class StageMapperTest {

    @InjectMocks
    private StageMapper stageMapper;

    @Test
    public void mapToStageDtoList() {
        //given
        List<Stage> stages = new ArrayList<>();
        stages.add(new Stage(1l,"stageName",10));

        //when
        List<StageDto> stageDtoList = stageMapper.mapToStageDtoList(stages);

        //then
        stageDtoList.stream().forEach(stageDto -> {
            assertEquals(1l, stageDto.getId());
            assertEquals("stageName", stageDto.getName());
            assertEquals(10, stageDto.getSeatsAmount());
        });
    }

    @Test
    public void mapToStage() {
        //given
        StageDto stageDto = new StageDto(1l,"stageName",10);

        //when
        Stage stage = stageMapper.mapToStage(stageDto);

        //then
        assertEquals("stageName", stage.getName());
        assertEquals(10, stage.getSeatsAmount());
    }

    @Test
    public void mapToStageDto() {
        //given
        Stage stage = new Stage(1l,"stageName",10);

        //when
        StageDto stageDto = stageMapper.mapToStageDto(stage);

        //then
        assertEquals(1l,stageDto.getId());
        assertEquals("stageName", stageDto.getName());
        assertEquals(10, stageDto.getSeatsAmount());
    }
}