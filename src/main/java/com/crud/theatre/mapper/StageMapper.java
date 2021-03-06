package com.crud.theatre.mapper;

import com.crud.theatre.domain.Stage;
import com.crud.theatre.domain.StageDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StageMapper {

    public List<StageDto> mapToStageDtoList(final List<Stage> stages) {
        return stages.stream().map(stage -> new StageDto(stage.getId(), stage.getName(), stage.getSeatsAmount()))
                .collect(Collectors.toList());
    }

    public Stage mapToStage(final StageDto stageDto) {
        return new Stage(stageDto.getId(), stageDto.getName(), stageDto.getSeatsAmount());
    }

    public StageDto mapToStageDto(final Stage stage) {
        if (stage != null) {
            return new StageDto(stage.getId(), stage.getName(), stage.getSeatsAmount());
        } else {
            return null;
        }
    }
}
