package com.crud.theatre.mapper;

import com.crud.theatre.domain.User;
import com.crud.theatre.domain.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Test
    public void mapToUser() {
        //Given
        UserDto userdto = new UserDto(1l, "firstName", "lastName",
                "test@test.com", "password");

        //when
        User user = userMapper.mapToUser(userdto);

        //then
        assertEquals("firstName", user.getFirstName());
        assertEquals("lastName", user.getLastName());
        assertEquals("test@test.com", user.getMail());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void mapToUserDto() {
        //Given
        User user = new User(1l, "firstName", "lastName",
                "test@test.com", "password");

        //when
        UserDto userDto = userMapper.mapToUserDtoWithOutPassword(user);

        //then
        assertEquals(1l, userDto.getId());
        assertEquals("firstName", userDto.getFirstName());
        assertEquals("lastName", userDto.getLastName());
        assertEquals("test@test.com", userDto.getMail());
        assertNull(userDto.getPassword());
    }

    @Test
    public void mapToUserDtoList() {
        List<User> users = new ArrayList<>();
        users.add(new User(1l, "firstName", "lastName",
                "test@test.com", "password"));

        //when
        List<UserDto> userDtoList = userMapper.mapToUserDtoListWithOutPassword(users);
        UserDto userDto = userDtoList.get(0);

        //then
        assertEquals(1l, userDto.getId());
        assertEquals("firstName", userDto.getFirstName());
        assertEquals("lastName", userDto.getLastName());
        assertEquals("test@test.com", userDto.getMail());
        assertNull(userDto.getPassword());
    }
}