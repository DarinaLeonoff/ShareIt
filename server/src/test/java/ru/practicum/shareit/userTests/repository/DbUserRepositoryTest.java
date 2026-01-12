package ru.practicum.shareit.userTests.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.DbUserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DbUserRepositoryTest {

    @Autowired
    private DbUserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = Generators.generateUser(1L);
    }

    @Test
    void testSaveUser() {
        // Сохраняем пользователя
        User savedUser = userRepository.save(testUser);
        testUser.setId(savedUser.getId());

        // Проверяем, что пользователь сохранен
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(testUser);
    }

    @Test
    void testFindById() {
        // Сохраняем пользователя
        User savedUser = userRepository.save(testUser);

        // Находим по ID
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        //пользователь сохранен и правильно воспроизведен из бд
        assertTrue(foundUser.isPresent());
        assertThat(foundUser.get()).usingRecursiveComparison().isEqualTo(savedUser);
    }

    @Test
    void testRemoveUser() {
        // Сохраняем пользователя
        User savedUser = userRepository.save(testUser);

        // Удаляем пользователя
        userRepository.removeUser(savedUser.getId());

        // Проверяем, что пользователь удален
        assertFalse(userRepository.findById(savedUser.getId()).isPresent());
    }

    @Test
    void testUpdateUser() {
        // Сохраняем пользователя
        User savedUser = userRepository.save(testUser);

        // Обновляем данные
        savedUser.setName("updatedUser");
        userRepository.save(savedUser);

        // Проверяем обновление
        User updatedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertEquals("updatedUser", updatedUser.getName());
    }

    @Test
    void testFindNonExistingUser() {
        // Пытаемся найти несуществующий ID
        Optional<User> nonExistingUser = userRepository.findById(-1L);
        assertFalse(nonExistingUser.isPresent());
    }
}
