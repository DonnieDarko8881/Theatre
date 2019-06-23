package com.crud.theatre.controller;

import com.crud.theatre.domain.Spectacle;
import com.crud.theatre.domain.SpectacleDto;
import com.crud.theatre.domain.Stage;
import com.crud.theatre.domain.StageDto;
import com.crud.theatre.mapper.SpectacleMapper;
import com.crud.theatre.mapper.StageMapper;
import com.crud.theatre.service.StageService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StageController.class)
public class StageControllerTest {

    @Autowired
    private Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StageService stageService;

    @MockBean
    private StageMapper stageMapper;

    @MockBean
    private SpectacleMapper spectacleMapper;

    @Test
    public void shouldCreateStage() throws Exception {
        //Given
        StageDto stageDto = new StageDto(1l,"Mala Sala", 10);

        //when&then
        mockMvc.perform(post("/v1/stages").contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(stageDto))
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
        verify(stageService,times(1)).save(any());
    }

    @Test
    public void shouldUpdateStage() throws  Exception{
        //Given
        StageDto stageDto = new StageDto(1l,"Mala Sala", 10);

        when(stageMapper.mapToStageDto(stageService.save(stageMapper.mapToStage(stageDto)))).thenReturn(stageDto);
        //when&then
        mockMvc.perform(put("/v1/stages").contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(stageDto))
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name",is("Mala Sala")))
                .andExpect(jsonPath("$.seatsAmount", is(10)));
    }

    @Test
    public void fetchEmptyStagesList() throws Exception {
        //Given
        List<StageDto> stageDtoList = Collections.emptyList();

        when(stageMapper.mapToStageDtoList(stageService.findAll())).thenReturn(stageDtoList);

        //When&Then
        mockMvc.perform(get("/v1/stages")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void fetchStagesList() throws Exception {
        //Given
        List<StageDto> stageDtoList =  new ArrayList<>();
        stageDtoList.add(new StageDto(1l,"test name", 10));

        when(stageMapper.mapToStageDtoList(stageService.findAll())).thenReturn(stageDtoList);

        //When&Then
        mockMvc.perform(get("/v1/stages")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",is("test name")))
                .andExpect(jsonPath("$[0].seatsAmount",is(10)));
    }

    @Test
    public void fetchEmptySpectaclesFromStageSet() throws Exception {
        //Given
        Stage stage = new Stage(1l,"Mala Sala", 10);
        Set<SpectacleDto>  spectacleDtoSet = Collections.emptySet();

        when(stageService.findById(anyLong())).thenReturn(stage);
        when(spectacleMapper.mapToSpectacleDtoSet(stage.getSpectacles())).thenReturn(spectacleDtoSet);

        //When&Then
        mockMvc.perform(get("/v1/stages/1/spectacles")
                .param("stageId","1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void fetchSpectaclesFromStageSet() throws Exception {
        //Given
        Stage stage = new Stage(1l,"Mala Sala", 10);
        Set<SpectacleDto>  spectacleDtoSet = new HashSet<>();
        spectacleDtoSet.add(new SpectacleDto(1l,"test name",1l));

        when(stageService.findById(anyLong())).thenReturn(stage);
        when(spectacleMapper.mapToSpectacleDtoSet(stage.getSpectacles())).thenReturn(spectacleDtoSet);

        //When&Then
        mockMvc.perform(get("/v1/stages/1/spectacles")
                .param("stageId","1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",is("test name")));
    }
}