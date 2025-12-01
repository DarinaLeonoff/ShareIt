package ru.practicum.shareit.userTests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.InMemoryUserRepository;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceImplTest {
    private UserService userService;

    @BeforeEach
    void cleanUp() {
        userService = new UserServiceImpl(new InMemoryUserRepository());
    }

    @Test
    void createAndGetUserTest() {
        User user = userService.createUser(Generators.generateUser(1L));
        User getUser = userService.getUser(user.getId());

        assertEquals(user, getUser);
    }

    @Test
    void editUserTest() {
        User user = userService.createUser(Generators.generateUser(1L));
        User userForUpdate = Generators.generateUser(user.getId());

        User updated = userService.editUser(userForUpdate, user.getId());
        assertEquals(userForUpdate, updated);
    }

    @Test
    void removeUserTest() {
        User user = userService.createUser(Generators.generateUser(1L));
        userService.removeUser(user.getId());

        assertThrows(NotFoundException.class, () -> {
            userService.getUser(user.getId());
        });
    }
}
