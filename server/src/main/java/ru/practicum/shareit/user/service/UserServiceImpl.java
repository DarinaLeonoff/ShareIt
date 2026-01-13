package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.DbUserRepository;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final DbUserRepository userRepository;
    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(DbUserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto user) {
        return mapper.mapUserToResponseDto(userRepository.save(mapper.mapDtoToUser(user)));
    }

    @Override
    @Transactional
    public UserResponseDto editUser(UserRequestDto dto, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден."));
        User updated = mapper.editUser(user, dto);
        return mapper.mapUserToResponseDto(userRepository.save(updated));
    }

    @Override
    public UserResponseDto getUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден."));
        log.info("Getting user: {}", user);
        return mapper.mapUserToResponseDto(user);
    }

    @Override
    @Transactional
    public void removeUser(long userId) {
        userRepository.removeUser(userId);
    }
}
