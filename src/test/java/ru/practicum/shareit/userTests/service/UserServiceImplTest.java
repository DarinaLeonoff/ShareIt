package ru.practicum.shareit.userTests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.repository.DbUserRepository;
import ru.practicum.shareit.user.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Mock
    private DbUserRepository userRepository;

    @Test
    void createAndGetUserTest() {
        UserResponseDto user = userService.createUser(Generators.generateUserRequestDto());
        UserResponseDto getUser = userService.getUser(user.getId());

        assertEquals(user, getUser);
    }

    @Test
    void editUserTest() {
        UserResponseDto user = userService.createUser(Generators.generateUserRequestDto());
        UserRequestDto userForUpdate = Generators.generateUserRequestDto();

        UserResponseDto updated = userService.editUser(userForUpdate, user.getId());

        assertEquals(updated.getId(), user.getId());
        assertEquals(updated.getName(), userForUpdate.getName());
        assertEquals(updated.getEmail(), userForUpdate.getEmail());
    }

    @Test
    void removeUserTest() {
        UserResponseDto user = userService.createUser(Generators.generateUserRequestDto());
        userService.removeUser(user.getId());

        assertThrows(NotFoundException.class, () -> {
            userService.getUser(user.getId());
        });
    }
}
