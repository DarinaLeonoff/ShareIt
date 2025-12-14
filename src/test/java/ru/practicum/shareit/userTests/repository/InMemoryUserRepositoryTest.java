package ru.practicum.shareit.userTests.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.exceptions.AlreadyExistsException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.InMemoryUserRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class InMemoryUserRepositoryTest {

    private UserRepository userRepository;
    private final UserMapper userMapper;

    @BeforeEach
    void cleanUp() {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    void createAndFindByIdTest() {
        User user = userRepository.createUser(userMapper.mapDtoToUser(Generators.generateUser(1L)));
        User getUser = userRepository.findById(user.getId()).orElseThrow(()->new NotFoundException("Пользователь не найден."));

        Assertions.assertEquals(user, getUser);
    }

    @Test
    void getWrongUserTest() {
        assertThrows(NotFoundException.class, () -> {
            userRepository.findById(99999L);
        });
    }

    @Test
    void editUserNameTest() {
        User user = userRepository.createUser(userMapper.mapDtoToUser(Generators.generateUser(1L)));

        User userForUpdate = new User();
        userForUpdate.setName("New Name for update");

        User updated = userRepository.editUser(userForUpdate);

        assertEquals(user.getId(), updated.getId());
        assertNotEquals(user.getName(), updated.getName());
        assertEquals(userForUpdate.getName(), updated.getName());
        assertEquals(user.getEmail(), updated.getEmail());
    }

    @Test
    void editUserEmailTest() {
        User user = userRepository.createUser(userMapper.mapDtoToUser(Generators.generateUser(1L)));

        User userForUpdate = new User();
        userForUpdate.setEmail("newEmail@ya.ru");

        User updated = userRepository.editUser(userForUpdate);

        assertEquals(user.getId(), updated.getId());
        assertNotEquals(user.getEmail(), updated.getEmail());
        assertEquals(userForUpdate.getEmail(), updated.getEmail());
        assertEquals(user.getName(), updated.getName());
    }

    @Test
    void editUserDuplicateEmailTest() {
        User user = userRepository.createUser(userMapper.mapDtoToUser(Generators.generateUser(1L)));
        User user2 = userRepository.createUser(userMapper.mapDtoToUser(Generators.generateUser(2L)));

        User userForUpdate = new User();
        userForUpdate.setEmail(user2.getEmail());

        assertThrows(AlreadyExistsException.class, () -> {
            userRepository.editUser(userForUpdate);
        });
    }

    @Test
    void removeUserTest() {
        User user1 = userRepository.createUser(userMapper.mapDtoToUser(Generators.generateUser(1L)));
        User user2 = userRepository.createUser(userMapper.mapDtoToUser(Generators.generateUser(2L)));

        userRepository.removeUser(user1.getId());

        assertEquals(userRepository.findById(user2.getId()), user2);
        assertThrows(NotFoundException.class, () -> {
            userRepository.findById(user1.getId());
        });
    }
}
