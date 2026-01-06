package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto user);

    UserResponseDto editUser(UserRequestDto dto, long userId);

    UserResponseDto getUser(long userId);

    void removeUser(long userId);
}
