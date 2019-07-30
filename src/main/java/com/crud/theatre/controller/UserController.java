package com.crud.theatre.controller;

import com.crud.theatre.domain.Success;
import com.crud.theatre.domain.UserDto;
import com.crud.theatre.mapper.UserMapper;
import com.crud.theatre.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/users", consumes = APPLICATION_JSON_VALUE)
    public void save(@RequestBody UserDto userDto) {
        userService.save(userMapper.mapToUser(userDto));
    }

    @GetMapping(value = "/users")
    public List<UserDto> findAll() {
        return userMapper.mapToUserDtoListWithOutPassword(userService.findAll());
    }

    @GetMapping(value = "/users/{mail}")
    public Success mailExist(@PathVariable String mail) {
        return userService.mailExist(mail);
    }

    @GetMapping(value = "/users/{mail}/{password}")
    public Success loginSuccess(@PathVariable String mail, @PathVariable String password) {
        return userService.login(mail, password);
    }

    @GetMapping(value = "/users/findBy/{mail}")
    public UserDto getUserByMail(@PathVariable String mail) {
        return userMapper.mapToUserDtoWithOutPassword(userService.findByMail(mail));
    }
}
