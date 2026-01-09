package ru.practicum.shareit.requestTest.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Set;

@SpringBootTest
public class ItemRequestTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testCreating() {
        ItemRequest req = ItemRequest.builder()
                .user(Generators.generateUser(1L))
                .description("test")
                .created(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(req);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyDescription(String description) {
        ItemRequest req = ItemRequest.builder()
                .user(Generators.generateUser(1L))
                .description(description)
                .created(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(req);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    void testEmptyUser(User user) {
        ItemRequest req = ItemRequest.builder()
                .user(user)
                .description("test")
                .created(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(req);
        Assertions.assertFalse(violations.isEmpty());
    }

}
