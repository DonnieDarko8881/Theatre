package com.crud.theatre.mapper;

import com.crud.theatre.domain.*;
import com.crud.theatre.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpectacleMapper {

    @Autowired
    private ActorMapper actorMapper;

    @Autowired
    private StageService stageService;


    public Spectacle mapToSpectacle(final SpectacleDto spectacleDto) {
        Stage stage = stageService.findById(spectacleDto.getStageId());
        return new Spectacle(spectacleDto.getName(), stage);
    }

    public SpectacleDto mapToSpectacleDto(final Spectacle spectacle) {
        return new SpectacleDto(spectacle.getId(), spectacle.getName(), spectacle.getStage().getId());
    }

    public List<SpectacleDto> mapToSpectacleDtoList(final List<Spectacle> spectacles) {
        return spectacles.stream()
                .map(spectacle -> new SpectacleDto(
                        spectacle.getId(),
                        spectacle.getName(),
                        spectacle.getStage().getId()))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<SpectacleDateDto> mapToDateDtoList(final List<SpectacleDate> spectacleDates){
        return spectacleDates.stream()
                .map(date -> new SpectacleDateDto(date.getId(), date.getDate()))
                .collect(Collectors.toList());
    }
}
