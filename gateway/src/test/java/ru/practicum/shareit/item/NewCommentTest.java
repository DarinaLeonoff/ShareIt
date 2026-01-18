package ru.practicum.shareit.item;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.dto.NewComment;

import java.util.Set;

@SpringBootTest
public class NewCommentTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private NewComment comment;

    @BeforeEach
    void setUp() {
        comment = NewComment.builder().text("New comment").build();
    }

    @Test
    void creatingValidationTest() {
        Set<ConstraintViolation<NewComment>> violations = validator.validate(comment);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void commentWithEmptyContentTest(String text) {
        comment.setText(text);
        Set<ConstraintViolation<NewComment>> violations = validator.validate(comment);
        Assertions.assertFalse(violations.isEmpty());
    }
}
