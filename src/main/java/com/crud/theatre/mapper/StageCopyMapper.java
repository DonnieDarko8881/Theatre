package com.crud.theatre.mapper;

import com.crud.theatre.domain.StageCopy;
import com.crud.theatre.domain.StageCopyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StageCopyMapper {

    private final SeatsMapper seatsMapper;
    private final SpectacleDateMapper spectacleDateMapper;

    @Autowired
    public StageCopyMapper(SeatsMapper seatsMapper, SpectacleDateMapper spectacleDateMapper) {
        this.seatsMapper = seatsMapper;
        this.spectacleDateMapper = spectacleDateMapper;
    }

    public List<StageCopyDto> mapToStageCopyDtoList(final List<StageCopy> stageCopies) {
        return stageCopies.stream()
                .map(copy -> new StageCopyDto(
                        copy.getId(),
                        seatsMapper.mapToSeatsDtoList(copy.getSeats()),
                        spectacleDateMapper.mapToSpectacleDateDto(copy.getSpectacleDate()),
                        copy.getSpectaclePricePLN()
                ))
                .collect(Collectors.toList());
    }
}
