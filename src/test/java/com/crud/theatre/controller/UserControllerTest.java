package com.crud.theatre.controller;

import com.crud.theatre.domain.Success;
import com.crud.theatre.domain.User;
import com.crud.theatre.domain.UserDto;
import com.crud.theatre.mapper.UserMapper;
import com.crud.theatre.service.UserService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserMapper mapper;

    @MockBean
    private UserService service;

    @Test
    public void fetchEmptyUserList() throws Exception {
        //Given
        List<UserDto> usersDto = Collections.emptyList();
        when(mapper.mapToUserDtoListWithOutPassword(service.findAll())).thenReturn(usersDto);
        //When&Then
        mockMvc.perform(get("/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void fetchUserList() throws Exception {
        //Given
        List<UserDto> usersDto = new ArrayList<>();
        usersDto.add(new UserDto(1l, "Test firstName", "Test lastName", "test@test.com"));

        when(mapper.mapToUserDtoListWithOutPassword(service.findAll())).thenReturn(usersDto);
        //When&Then
        mockMvc.perform(get("/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Test firstName")))
                .andExpect(jsonPath("$[0].lastName", is("Test lastName")))
                .andExpect(jsonPath("$[0].mail", is("test@test.com")))
                .andExpect(jsonPath("$[0].password", nullValue()));
    }

    @Test
    public void shouldReturnSuccessTrueMailExisting() throws Exception {
        //Give
        when(service.mailExist(anyString())).thenReturn(new Success(true));
        //When&Then
        mockMvc.perform(get("/v1/users/test@test.com")
                .param("mail", "test@test.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void shouldReturnSuccessFalseMailExisting() throws Exception {
        //Give
        when(service.mailExist(anyString())).thenReturn(new Success(false));
        //When&Then
        mockMvc.perform(get("/v1/users/test@test.com")
                .param("mail", "test@test.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    public void shouldLoginReturnTrue() throws Exception {
        //Give
        when(service.login(anyString(), anyString())).thenReturn(new Success(true));
        //When&Then
        mockMvc.perform(get("/v1/users/test@test.com/password")
                .param("mail", "test@test.com")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void shouldLoginReturnFalse() throws Exception {
        //Give
        when(service.login(anyString(), anyString())).thenReturn(new Success(false));
        //When&Then
        mockMvc.perform(get("/v1/users/test@test.com/password")
                .param("mail", "test@test.com")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    public void shouldReturnUserByMail() throws Exception {
        //Given
        UserDto userDto = new UserDto(1l, "Test firstName", "Test lastName", "test@test.com");
        User user = new User(1l, "Test firstName", "Test lastName", "test@test.com", "test password");

        when(service.findByMail(anyString())).thenReturn(user);
        when(mapper.mapToUserDtoWithOutPassword(any(User.class))).thenReturn(userDto);

        //When&Then
        mockMvc.perform(get("/v1/users/findBy/test@test.com")
                .param("mail", "test@test.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Test firstName")))
                .andExpect(jsonPath("$.lastName", is("Test lastName")))
                .andExpect(jsonPath("$.mail", is("test@test.com")))
                .andExpect(jsonPath("$.password", nullValue()));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        //Given
        UserDto userDto = new UserDto(1l, "Test firstName", "Test lastName", "test@test.com", "test password");

        //When&Then
        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(gson.toJson(userDto)))
                .andExpect(status().isOk());
        verify(service, times(1)).save(any());
    }
}