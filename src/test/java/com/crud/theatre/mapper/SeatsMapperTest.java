package com.crud.theatre.mapper;

import com.crud.theatre.domain.Seats;
import com.crud.theatre.domain.SeatsDto;
import com.crud.theatre.domain.StageCopy;
import com.crud.theatre.domain.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SeatsMapperTest {

    @InjectMocks
    private SeatsMapper seatsMapper;

    @Test
    public void mapToSeatsDtoList() {
        //Given
        StageCopy stageCopy = StageCopy.builder().id(3l).build();

        Set<Seats> seatsSet = new HashSet<>();
        seatsSet.add(new Seats(1l, 2, stageCopy, Status.FREE.toString()));

        //when
        List<SeatsDto> seatsDtoList = seatsMapper.mapToSeatsDtoList(seatsSet);

        //then
        SeatsDto seatsDto = seatsDtoList.get(0);

        assertEquals(1, seatsDtoList.size());
        assertEquals(1l, seatsDto.getId());
        assertEquals(2, seatsDto.getNumber());
        assertEquals(3l, seatsDto.getStageCopyId());
        assertEquals(Status.FREE.toString(), seatsDto.getStatus());
    }
}
