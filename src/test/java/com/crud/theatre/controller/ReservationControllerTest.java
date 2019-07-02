package com.crud.theatre.controller;


import com.crud.theatre.Facade.ReservationFacade;
import com.crud.theatre.domain.ReservationDto;
import com.google.gson.Gson;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationFacade facade;

    @Test
    public void shouldCreateReservation() throws Exception {
        //Given
        ReservationDto reservationDto = new ReservationDto(1l, 2l, 4);

        mockMvc.perform(post("/v1/reservations").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(gson.toJson(reservationDto)))
                .andExpect(status().isOk());
        verify(facade, times(1)).createReservation(any());
    }

    @Test
    public void fetchEmptyReservationList() throws Exception {
        //Given
        List<ReservationDto> reservationDtoList = Collections.emptyList();
        when(facade.findAll()).thenReturn(reservationDtoList);
        //When&Then
        mockMvc.perform(get("/v1/reservations").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void fetchReservationList() throws Exception {
        //Given
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        ReservationDto reservationDto = new ReservationDto(1l, LocalDateTime.parse("2019-06-15T19:00:07"),
                2l, 3l, 4l, 5);
        reservationDtoList.add(reservationDto);

        when(facade.findAll()).thenReturn(reservationDtoList);

        //When&Then
        mockMvc.perform(get("/v1/reservations").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].reservationId", is(1)))
                .andExpect(jsonPath("$[0].reservationDate", is("2019-06-15T19:00:07")))
                .andExpect(jsonPath("$[0].userId", is(2)))
                .andExpect(jsonPath("$[0].stageCopyId", is(3)))
                .andExpect(jsonPath("$[0].seatsId", is(4)))
                .andExpect(jsonPath("$[0].seatsNumber", is(5)));
    }
}