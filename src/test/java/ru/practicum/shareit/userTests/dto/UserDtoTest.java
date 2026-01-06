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
import ru.practicum.shareit.user.dto.UserRequestDto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserDtoTest {

    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void validationTest() {
        UserRequestDto user = Generators.generateUserRequestDto();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(user);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void validationNameTest(String name) {
        UserRequestDto user = Generators.generateUserRequestDto();
        user.setName(name);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"emailyandex.ru", "email@", "@yandex.ru"})
    public void validationEmailTest(String email) {
        UserRequestDto user = Generators.generateUserRequestDto();
        user.setEmail(email);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void testDefaultValues() {
        UserRequestDto user = new UserRequestDto();

        // Проверяем значения по умолчанию
        assertNull(user.getName());
        assertNull(user.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        UserRequestDto user = new UserRequestDto();

        // Устанавливаем значения
        user.setName("Test Item");
        user.setEmail("Test@ya.ru");

        // Проверяем полученные значения
        assertEquals("Test Item", user.getName());
        assertEquals("Test@ya.ru", user.getEmail());
    }
}
