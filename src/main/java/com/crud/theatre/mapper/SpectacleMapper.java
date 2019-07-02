package com.crud.theatre.mapper;

import com.crud.theatre.domain.*;
import com.crud.theatre.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SpectacleMapper {

    private final StageService stageService;

    @Autowired
    public SpectacleMapper(StageService stageService) {
        this.stageService = stageService;
    }

    public Spectacle mapToSpectacle(final SpectacleDto spectacleDto) {
        Stage stage = stageService.findById(spectacleDto.getStageId());
        return new Spectacle(spectacleDto.getName(), stage);
    }

    public List<SpectacleDto> mapToSpectacleDtoList(final List<Spectacle> spectacles) {
        return spectacles.stream()
                .map(spectacle -> new SpectacleDto(
                        spectacle.getId(),
                        spectacle.getName(),
                        spectacle.getStage().getId()))
                .collect(Collectors.toList());
    }

    public Set<SpectacleDto> mapToSpectacleDtoSet(final Set<Spectacle> spectacles) {
        return spectacles.stream()
                .map(spectacle -> new SpectacleDto(
                        spectacle.getId(),
                        spectacle.getName(),
                        spectacle.getStage().getId()))
                .collect(Collectors.toSet());
    }

    public List<SpectacleDateDto> mapToDateDtoList(final List<SpectacleDate> spectacleDates){
        return spectacleDates.stream()
                .map(date -> SpectacleDateDto.builder()
                        .id(date.getId())
                        .date(date.getDate())
                        .build())
                .collect(Collectors.toList());
    }
}
