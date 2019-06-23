package com.crud.theatre.controller;

import com.crud.theatre.Facade.ActorFacade;
import com.crud.theatre.domain.ActorDto;
import com.crud.theatre.domain.SpectacleDto;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ActorController.class)
public class ActorControllerTest {

    @Autowired
    private Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorFacade facade;

    @Test
    public void fetchEmptyActorList() throws Exception {
        //Given
        List<ActorDto> actorDtoList = Collections.emptyList();

        when(facade.findAll()).thenReturn(actorDtoList);

        //When&Then
        mockMvc.perform(get("/v1/actors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void fetchActorList() throws Exception {
        //Given
        List<ActorDto> actorDtoList = new ArrayList<>();
        actorDtoList.add(new ActorDto(1l, "firstName test", "lastName test"));

        when(facade.findAll()).thenReturn(actorDtoList);

        //When&Then
        mockMvc.perform(get("/v1/actors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("firstName test")))
                .andExpect(jsonPath("$[0].lastName", is("lastName test")));

    }

    @Test
    public void fetchEmptySpectacleList() throws Exception {
        //Given
        List<SpectacleDto> spectacleDtoList = Collections.emptyList();

        when(facade.getSpectacles(anyLong())).thenReturn(spectacleDtoList);

        //When&Then
        mockMvc.perform(get("/v1/actors/1/spectacles")
                .param("actorId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void fetchSpectacleList() throws Exception {
        //Given
        List<SpectacleDto> spectacleDtoList = new ArrayList<>();
        spectacleDtoList.add(new SpectacleDto(1l, "name test", 1l));

        when(facade.getSpectacles(anyLong())).thenReturn(spectacleDtoList);

        //When&Then
        mockMvc.perform(get("/v1/actors/1/spectacles")
                .param("actorId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("name test")))
                .andExpect(jsonPath("$[0].stageId", is(1)));
    }

    @Test
    public void shouldCreateActor() throws Exception {
        //Given
        ActorDto actorDto = new ActorDto("firstName test", "lastName test");

        //When&Then
        mockMvc.perform(post("/v1/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(gson.toJson(actorDto)))
                .andExpect(status().isOk());
        verify(facade, times(1)).saveActor(ArgumentMatchers.any());
    }

    @Test
    public void shouldDeleteActor() throws Exception {
            //Given
            ActorDto actorDto = new ActorDto(1l, "firstName test", "lastName test");

            //When&Then
            mockMvc.perform(delete("/v1/actors/1")
                    .param("actorId", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(gson.toJson(actorDto)))
                    .andExpect(status().isOk());
            verify(facade, times(1)).deleteActor(1l);
    }
}