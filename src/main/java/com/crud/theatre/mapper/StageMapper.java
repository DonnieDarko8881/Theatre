package com.crud.theatre.mapper;

import com.crud.theatre.domain.Stage;
import com.crud.theatre.domain.StageDto;
import org.springframework.stereotype.Component;

@Component
public class StageMapper {


    public Stage mapToStage(final StageDto stageDto){
        return new Stage(stageDto.getName(), stageDto.getSeatsAmount());
    }

    public StageDto mapToStageDto(final Stage stage){
        return new StageDto(stage.getId(),stage.getName(),stage.getSeatsAmount());
    }
}
