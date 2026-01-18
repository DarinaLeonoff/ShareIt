package ru.practicum.shareit.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.dto.UserRequest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserRequestTest {

    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private UserRequest user;

    @BeforeEach
    void setUp() {
        user = UserRequest.builder()
                .name("Name")
                .email("Email@mail.ru")
                .build();
    }

    @Test
    public void validationTest() {
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(user);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void validationNameTest(String name) {
        user.setName(name);

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"emailyandex.ru", "email@", "@yandex.ru"})
    public void validationEmailTest(String email) {
        user.setEmail(email);

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void testDefaultValues() {
        UserRequest user1 = new UserRequest();

        // Проверяем значения по умолчанию
        assertNull(user1.getName());
        assertNull(user1.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        UserRequest user1 = new UserRequest();

        // Устанавливаем значения
        user1.setName("Test Item");
        user1.setEmail("Test@ya.ru");

        // Проверяем полученные значения
        assertEquals("Test Item", user1.getName());
        assertEquals("Test@ya.ru", user1.getEmail());
    }
}
