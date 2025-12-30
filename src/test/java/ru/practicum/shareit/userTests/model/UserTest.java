package ru.practicum.shareit.userTests.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {

    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    @Autowired
    private UserMapper mapper;

    @Test
    public void validationTest() {
        User user = mapper.mapDtoToUser(Generators.generateUser(1L));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void validationNameTest(String name) {
        User user = mapper.mapDtoToUser(Generators.generateUser(1L));
        user.setName(name);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"emailyandex.ru", "email@", "@yandex.ru"})
    public void validationEmailTest(String email) {
        User user = mapper.mapDtoToUser(Generators.generateUser(1L));
        user.setEmail(email);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
    }

    //getter, setter, hash and toString tests

    @Test
    void testDefaultValues() {
        User user = new User();

        // Проверяем значения по умолчанию
        assertEquals(0L, user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();

        // Устанавливаем значения
        user.setId(123L);
        user.setName("Test Item");
        user.setEmail("Test@ya.ru");

        // Проверяем полученные значения
        assertEquals(123L, user.getId());
        assertEquals("Test Item", user.getName());
        assertEquals("Test@ya.ru", user.getEmail());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = mapper.mapDtoToUser(Generators.generateUser(1L));
        User user2 = mapper.mapDtoToUser(Generators.generateUser(1L));
        User user3 = mapper.mapDtoToUser(Generators.generateUser(2L));

        assertTrue(user1.equals(user1)); // Рефлексивность
        assertTrue(user1.equals(user2)); // Симметричность
        assertFalse(user1.equals(user3)); // Разные объекты
        assertFalse(user1.equals(null));

        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    void testToString() {
        User user = mapper.mapDtoToUser(Generators.generateUser(1L));

        String expected = "User(id=" + user.getId() + ", name=" + user.getName() + ", email=" + user.getEmail() + ")";
        assertEquals(expected, user.toString());
    }

}
