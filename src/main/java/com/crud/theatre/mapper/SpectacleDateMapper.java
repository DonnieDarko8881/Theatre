package com.crud.theatre.mapper;

import com.crud.theatre.domain.SpectacleDate;
import com.crud.theatre.domain.SpectacleDateDto;
import com.crud.theatre.domain.SpectacleDto;
import com.crud.theatre.domain.StageCopyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpectacleDateMapper {

    private final StageMapper stageMapper;

    @Autowired
    public SpectacleDateMapper(StageMapper stageMapper) {
        this.stageMapper = stageMapper;
    }

    public List<SpectacleDateDto> mapToSpectacleDateDtoList(final List<SpectacleDate> spectacleDates) {
        return spectacleDates.stream()
                .map(date -> new SpectacleDateDto(
                        date.getId(),
                        date.getDate(),
                        mapToSpectacleDto(date),
                        stageMapper.mapToStageDto(date.getStage()),
                        mapToStageCopyDto(date)
                ))
                .collect(Collectors.toList());
    }

    private StageCopyDto mapToStageCopyDto(final SpectacleDate date) {
        try {
            return StageCopyDto.builder()
                    .id(date.getStageCopy().getId())
                    .build();
        } catch (NullPointerException e) {
            return null;
        }
    }

    private SpectacleDto mapToSpectacleDto(final SpectacleDate date) {
        return new SpectacleDto(date.getSpectacle().getId(), date.getSpectacle().getName());
    }

    public SpectacleDateDto mapToSpectacleDateDto(final SpectacleDate spectacleDate) {
        return SpectacleDateDto.builder()
                .id(spectacleDate.getId())
                .date(spectacleDate.getDate())
                .spectacleDto(mapToSpectacleDto(spectacleDate))
                .stageDto(stageMapper.mapToStageDto(spectacleDate.getStage()))
                .stageCopyDto(mapToStageCopyDto(spectacleDate))
                .build();
    }
}
