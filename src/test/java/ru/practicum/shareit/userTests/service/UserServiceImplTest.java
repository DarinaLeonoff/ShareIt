package ru.practicum.shareit.userTests.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.DbUserRepository;
import ru.practicum.shareit.user.repository.InMemoryUserRepository;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest

public class UserServiceImplTest {

    @Autowired
    private final UserService userService;

    public UserServiceImplTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    void createAndGetUserTest() {
        UserDto user = userService.createUser(Generators.generateUser(1L));
        UserDto getUser = userService.getUser(user.getId());

        assertEquals(user, getUser);
    }

    @Test
    void editUserTest() {
        UserDto user = userService.createUser(Generators.generateUser(1L));
        UserDto userForUpdate = Generators.generateUser(user.getId());

        UserDto updated = userService.editUser(userForUpdate, user.getId());
        assertEquals(userForUpdate, updated);
    }

    @Test
    void removeUserTest() {
        UserDto user = userService.createUser(Generators.generateUser(1L));
        userService.removeUser(user.getId());

        assertThrows(NotFoundException.class, () -> {
            userService.getUser(user.getId());
        });
    }
}
