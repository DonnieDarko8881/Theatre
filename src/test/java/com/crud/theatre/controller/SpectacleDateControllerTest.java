package com.crud.theatre.controller;

import com.crud.theatre.Facade.SpectacleDateFacade;
import com.crud.theatre.domain.SpectacleDateDto;
import com.crud.theatre.domain.SpectacleDto;
import com.crud.theatre.domain.StageCopyDto;
import com.crud.theatre.domain.StageDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SpectacleDateController.class)
public class SpectacleDateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpectacleDateFacade facade;

    @Test
    public void fetchEmptyListSpectacleDates() throws Exception {
        //Given
        List<SpectacleDateDto> spectacleDateDtoList = Collections.emptyList();

        when(facade.getSpectaclesDates()).thenReturn(spectacleDateDtoList);

        //When&Then
        mockMvc.perform(get("/v1/dates")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void fetchSpectacleDatesWithNullStageCopyDto() throws Exception {
        //Given
        SpectacleDto spectacleDto = new SpectacleDto(1L, "Spectacle Test");
        StageDto stageDto = new StageDto(1l, "Stage Test", 10);
        LocalDateTime date = LocalDateTime.now();

        List<SpectacleDateDto> spectacleDateDtoList = new ArrayList<>();
        spectacleDateDtoList.add(new SpectacleDateDto(1l, date, spectacleDto, stageDto, null));

        when(facade.getSpectaclesDates()).thenReturn(spectacleDateDtoList);

        //When&Then
        mockMvc.perform(get("/v1/dates")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].date", is(date.toString())))
                .andExpect(jsonPath("$[0].spectacleDto.name", is("Spectacle Test")))
                .andExpect(jsonPath("$[0].stageCopyDto", is(nullValue())));
    }

    @Test
    public void fetchSpectacleDatesWithStageCopyDto() throws Exception {
        //Given
        SpectacleDto spectacleDto = new SpectacleDto(1L, "Spectacle Test");
        StageDto stageDto = new StageDto(1l, "Stage Test", 10);
        LocalDateTime date = LocalDateTime.parse("2018-10-10T13:50");
        StageCopyDto stageCopyDto = StageCopyDto.builder().id(1l).build();

        List<SpectacleDateDto> spectacleDateDtoList = new ArrayList<>();
        spectacleDateDtoList.add(new SpectacleDateDto(1l, date, spectacleDto, stageDto, stageCopyDto));

        when(facade.getSpectaclesDates()).thenReturn(spectacleDateDtoList);

        //When&Then
        mockMvc.perform(get("/v1/dates")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].spectacleDto.name", is("Spectacle Test")))
                .andExpect(jsonPath("$[0].stageCopyDto.id", is(1)));
    }

    @Test
    public void shouldDeleteSpectacleDate() throws Exception {

        //When&Then
        mockMvc.perform(delete("/v1/dates/1")
                .param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(facade, times(1)).deleteSpectacleDate(anyLong());
    }

}