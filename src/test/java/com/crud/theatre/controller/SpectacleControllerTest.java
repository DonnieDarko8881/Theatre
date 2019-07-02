package com.crud.theatre.controller;


import com.crud.theatre.Facade.SpectacleFacade;
import com.crud.theatre.domain.ActorDto;
import com.crud.theatre.domain.SpectacleDateDto;
import com.crud.theatre.domain.SpectacleDto;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SpectacleController.class)
public class SpectacleControllerTest {

    @Autowired
    private Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpectacleFacade facade;

    @Test
    public void shouldCreateSpectacle() throws Exception {
        //Given
        SpectacleDto spectacleDto = new SpectacleDto(1l, "name test", 1l);

        //When&Then
        mockMvc.perform(post("/v1/spectacles")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(gson.toJson(spectacleDto)))
                .andExpect(status().isOk());
        verify(facade, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    public void fetchEmptySpectacleList() throws Exception {
        //Given
        List<SpectacleDto> spectacleDtoList = Collections.emptyList();

        when(facade.findAll()).thenReturn(spectacleDtoList);

        //When&Then
        mockMvc.perform(get("/v1/spectacles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void fetchSpectacleList() throws Exception {
        //Given
        List<SpectacleDto> spectacleDtoList = new ArrayList<>();
        spectacleDtoList.add(new SpectacleDto(1l, "name test", 1l));

        when(facade.findAll()).thenReturn(spectacleDtoList);

        //When&Then
        mockMvc.perform(get("/v1/spectacles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("name test")))
                .andExpect(jsonPath("$[0].stageId", is(1)));
    }

    @Test
    public void fetchEmptyCast() throws Exception {
        //Given
        List<ActorDto> cast = Collections.emptyList();

        when(facade.getCast(anyLong())).thenReturn(cast);

        //When&Then
        mockMvc.perform(get("/v1/spectacles/1/cast")
                .param("spectacleId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void fetchCast() throws Exception {
        //Given
        List<ActorDto> cast = new ArrayList<>();
        cast.add(new ActorDto(1l, "firstName test", "lastName test"));

        when(facade.getCast(anyLong())).thenReturn(cast);

        //When&Then
        mockMvc.perform(get("/v1/spectacles/1/cast")
                .param("spectacleId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("firstName test")))
                .andExpect(jsonPath("$[0].lastName", is("lastName test")));
    }

    @Test
    public void fetchEmptySpectacleDateList() throws Exception {
        //Given
        List<SpectacleDateDto> spectacleDateDtoList = Collections.emptyList();

        when(facade.getDates(anyLong())).thenReturn(spectacleDateDtoList);

        //When&Then
        mockMvc.perform(get("/v1/spectacles/1/dates")
                .param("spectacleId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void fetchSpectacleDateList() throws Exception {
        //Given
        List<SpectacleDateDto> spectacleDateDtoList = new ArrayList<>();
        spectacleDateDtoList.add(SpectacleDateDto.builder()
                .id(1l)
                .date(LocalDateTime.parse("2000-03-10T18:35:00"))
                .build());

        when(facade.getDates(anyLong())).thenReturn(spectacleDateDtoList);

        //When&Then
        mockMvc.perform(get("/v1/spectacles/1/dates")
                .param("spectacleId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].date", is("2000-03-10T18:35:00")));
    }

    @Test
    public void shouldAddActorToCast() throws Exception {

        //When&Then
        mockMvc.perform(put("/v1/spectacles/1/actors/1")
                .param("spectaclesId", "1")
                .param("actorId", "1"))
                .andExpect(status().isOk());
        verify(facade, times(1)).addActorToCast(1l, 1l);
    }

    @Test
    public void shouldCreateSpectacleDate() throws Exception {
        //Given
        LocalDateTime now = LocalDateTime.now();
        SpectacleDateDto spectacleDateDto = SpectacleDateDto.builder().date(now).build();

        //When&Then
        mockMvc.perform(post("/v1/spectacles/1/dates")
                .param("spectacleId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(gson.toJson(spectacleDateDto)))
                .andExpect(status().isOk());
        verify(facade, times(1)).saveSpectacleDate(anyLong(), any());
    }

    @Test
    public void shouldDeleteSpectacle() throws Exception {

        //When&Then
        mockMvc.perform(delete("/v1/spectacles/1")
                .param("spectacleId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(facade, times(1)).delete(1l);
    }
}