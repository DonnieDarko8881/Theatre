package com.crud.theatre.mapper;

import com.crud.theatre.domain.Seats;
import com.crud.theatre.domain.SeatsDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class SeatsMapper {

    public List<SeatsDto> mapToSeatsDtoList(final Set<Seats> seatsSet) {
        return seatsSet.stream()
                .map(seats -> new SeatsDto(
                        seats.getId(),
                        seats.getNumber(),
                        seats.getStageCopy().getId(),
                        seats.getStatus()))
                .collect(Collectors.toList());
    }
}
