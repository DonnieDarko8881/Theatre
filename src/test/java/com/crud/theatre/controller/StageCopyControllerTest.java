package com.crud.theatre.controller;

import com.crud.theatre.domain.*;
import com.crud.theatre.mapper.StageCopyMapper;
import com.crud.theatre.service.StageCopyService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StageCopyController.class)
public class StageCopyControllerTest {

    @Autowired
    private Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StageCopyService stageCopyService;

    @MockBean
    private StageCopyMapper mapper;

    @Test
    public void fetchEmptyStageCopyList() throws Exception {
        //Given
        List<StageCopyDto> stageCopyDtos = Collections.emptyList();
        when(mapper.mapToStageCopyDtoList(stageCopyService.findAll())).thenReturn(stageCopyDtos);
        //When&Then
        mockMvc.perform(get("/v1/stageCopies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void fetchStageCopyList() throws Exception {
        //Given
        List<SeatsDto> seatsDtos = new ArrayList<>();
        seatsDtos.add(new SeatsDto(1L,1,1l, Status.FREE.toString()));
        SpectacleDto spectacleDto = new SpectacleDto(1l,"test spectacleName", null);
        StageDto stageDto = new StageDto(1l,"TestName",10);
        SpectacleDateDto spectacleDateDto = new SpectacleDateDto(2L, LocalDateTime.parse("2000-10-10T10:40:00"),
                spectacleDto,stageDto,null);

        List<StageCopyDto> stageCopyDtos = new ArrayList<>();
        stageCopyDtos.add(new StageCopyDto(1L,seatsDtos,spectacleDateDto,new BigDecimal(80)));

        when(mapper.mapToStageCopyDtoList(stageCopyService.findAll())).thenReturn(stageCopyDtos);
        //When&Then
        mockMvc.perform(get("/v1/stageCopies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].seats", hasSize(1)))
                .andExpect(jsonPath("$[0].spectacleDateDto.date",is("2000-10-10T10:40:00")))
                .andExpect(jsonPath("$[0].spectacleDateDto.id",is(2)))
                .andExpect(jsonPath("$[0].spectaclePricePLN",is(80)));
    }

    @Test
    public void shouldChangeStatusSeats() throws Exception {

        //When&Then
        mockMvc.perform(put("/v1/stageCopies/1/seats/1/status/PAID")
                .param("stageCopyId","1")
                .param("seatsId","1")
                .param("status","PAID"))
                .andExpect(status().isOk());
        verify(stageCopyService, times(1)).changeSeatsStatus(1l,1l,"PAID");
    }

    @Test
    public void shouldCreateStageCopy() throws Exception {

        //When&Then
        mockMvc.perform(post("/v1/stageCopies/1/dates/1/price/80")
                .param("stageId","1")
                .param("dateId","1")
                .param("spectaclePrice","80"))
                .andExpect(status().isOk());
        verify(stageCopyService, times(1)).saveStageCopy(1,1,new BigDecimal(80));
    }
}