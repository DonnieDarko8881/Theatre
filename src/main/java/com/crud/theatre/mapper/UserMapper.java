package com.crud.theatre.mapper;

import com.crud.theatre.domain.User;
import com.crud.theatre.domain.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User mapToUser(UserDto userDto) {
        return new User(userDto.getFirstName(), userDto.getLastName(),
                userDto.getMail(), userDto.getPassword());
    }

    public UserDto mapToUserDtoWithOutPassword(final User user){
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMail());
    }

    public List<UserDto> mapToUserDtoListWithOutPassword(final List<User> users) {
        return users.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getMail())
                )
                .collect(Collectors.toList());
    }
}
