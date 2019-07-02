package com.crud.theatre.mapper;

import com.crud.theatre.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StageCopyMapperTest {

    @InjectMocks
    private StageCopyMapper stageCopyMapper;

    @Mock
    private SeatsMapper seatsMapper;

    @Mock
    SpectacleDateMapper spectacleDateMapper;

    @Test
    public void mapToStageCopyDtoList() {
        //Given
        Stage stage = new Stage(1l, "stageName", 10);
        SpectacleDate spectacleDate = SpectacleDate.builder()
                .id(1l)
                .date(LocalDateTime.parse("2000-10-10T16:50"))
                .spectacle(new Spectacle(2l, "test name", stage, new ArrayList<>(), new ArrayList<>()))
                .stage(stage)
                .stageCopy(null)
                .build();

        SpectacleDateDto spectacleDateDto = SpectacleDateDto.builder()
                .id(1l)
                .date(LocalDateTime.parse("2000-10-10T16:50"))
                .spectacleDto(new SpectacleDto(2l, "spectacleName", 1l))
                .stageDto(new StageDto(1l, "stageName", 10))
                .stageCopyDto(StageCopyDto.builder().id(1l).build())
                .build();

        Set<Seats> seats = new HashSet<>();
        StageCopy stageCopy = StageCopy.builder()
                .id(1l)
                .stage(stage)
                .seats(seats)
                .spectacleDate(spectacleDate)
                .spectaclePricePLN(new BigDecimal(80))
                .build();
        seats.add(new Seats(2, stageCopy, Status.FREE.toString()));
        spectacleDate.setStageCopy(stageCopy);

        List<StageCopy> stageCopyList = new ArrayList<>();
        stageCopyList.add(stageCopy);

        List<SeatsDto> seatsDtoList = new ArrayList<>();
        seatsDtoList.add(new SeatsDto(10l, 2, 1l, Status.FREE.toString()));

        when(seatsMapper.mapToSeatsDtoList(seats)).thenReturn(seatsDtoList);
        when(spectacleDateMapper.mapToSpectacleDateDto(spectacleDate)).thenReturn(spectacleDateDto);

        //when
        List<StageCopyDto> stageCopyDtoList = stageCopyMapper.mapToStageCopyDtoList(stageCopyList);

        //then
        StageCopyDto stageCopyDto = stageCopyDtoList.get(0);
        assertEquals(1l, stageCopyDto.getId());
        //stageCopyDto.getSpectaclePricePLN()
        assertEquals(new BigDecimal(80), stageCopyDto.getSpectaclePricePLN());
        //SpectacleDateDto.Date
        assertEquals(LocalDateTime.parse("2000-10-10T16:50"), stageCopyDto.getSpectacleDateDto().getDate());
        //SpectacleDateDto.SeatsDto
        SeatsDto seatsDto = stageCopyDto.getSeats().get(0);
        assertEquals(10l, seatsDto.getId());
        assertEquals(2, seatsDto.getNumber());
        assertEquals(Status.FREE.toString(), seatsDto.getStatus());
        //SpectacleDateDto.stageDto
        StageDto stageDto = stageCopyDto.getSpectacleDateDto().getStageDto();
        assertEquals(1l, stageDto.getId());
        assertEquals("stageName", stageDto.getName());
        assertEquals(10, stageDto.getSeatsAmount());
        //SpectacleDateDto.SpectacleDto
        SpectacleDto spectacleDto = stageCopyDto.getSpectacleDateDto().getSpectacleDto();
        assertEquals(2l, spectacleDto.getId());
        assertEquals("spectacleName", spectacleDto.getName());
        assertEquals(1l, spectacleDto.getStageId().longValue());
        //SpectacleDateDto.stageCopyDto
        long stageCopyDtoId = stageCopyDto.getSpectacleDateDto().getStageCopyDto().getId();
        assertEquals(1l, stageCopyDtoId);
    }
}
