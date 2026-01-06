package ru.practicum.shareit.userTests.dto;

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
import ru.practicum.shareit.user.dto.UserResponseDto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserResponseDtoTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void validationTest() {
        UserResponseDto user = Generators.generateUserResponseDto(1L);

        Set<ConstraintViolation<UserResponseDto>> violations = validator.validate(user);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void validationNameTest(String name) {
        UserResponseDto user = Generators.generateUserResponseDto(1L);
        user.setName(name);

        Set<ConstraintViolation<UserResponseDto>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L})
    public void validationIdTest(long id) {
        UserResponseDto user = Generators.generateUserResponseDto(1L);
        user.setId(id);

        Set<ConstraintViolation<UserResponseDto>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"emailyandex.ru", "email@", "@yandex.ru"})
    public void validationEmailTest(String email) {
        UserResponseDto user = Generators.generateUserResponseDto(1L);;
        user.setEmail(email);

        Set<ConstraintViolation<UserResponseDto>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void testDefaultValues() {
        UserResponseDto user = new UserResponseDto();

        // Проверяем значения по умолчанию
        assertNull(user.getName());
        assertNull(user.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        UserResponseDto user = new UserResponseDto();

        // Устанавливаем значения
        user.setName("Test Item");
        user.setEmail("Test@ya.ru");

        // Проверяем полученные значения
        assertEquals("Test Item", user.getName());
        assertEquals("Test@ya.ru", user.getEmail());
    }
}
