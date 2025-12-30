package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto editUser(UserDto dto, long userId);

    UserDto getUser(long userId);

    void removeUser(long userId);
}
