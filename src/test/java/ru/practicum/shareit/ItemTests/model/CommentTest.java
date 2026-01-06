package ru.practicum.shareit.ItemTests.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Set;

@SpringBootTest
public class CommentTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void trueIfValidComment() {
        // Создаем валидный объект Comment
        Item item = Item.builder().build();
        item.setId(1L);

        User user = new User();
        user.setId(1L);

        Comment comment = Comment.builder().item(item).user(user).text("Хороший комментарий").created(LocalDateTime.now()).build();

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void falseIfInvalidComment(String text) {
        Item item = Item.builder().build();
        item.setId(1L);

        User user = new User();
        user.setId(1L);

        Comment comment = Comment.builder().item(item).user(user).text(text).created(LocalDateTime.now()).build();

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void falseIfItemIsNull() {
        // Проверяем, что item не может быть null
        User user = new User();
        user.setId(1L);

        Comment comment =
                Comment.builder().item(null).user(user).text("some text").created(LocalDateTime.now()).build();

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void falseIdUserIsNull() {
        // Проверяем, что user не может быть null
        Item item = Item.builder().build();
        item.setId(1L);

        Comment comment =
                Comment.builder().item(item).user(null).text("some text").created(LocalDateTime.now()).build();

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void falseIfCreateDataIsNull() {
        // Проверяем, что created может быть null
        Item item = Item.builder().build();
        item.setId(1L);

        User user = new User();
        user.setId(1L);

        Comment comment =
                Comment.builder().item(item).user(user).text("some test").created(null).build();

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        Assertions.assertFalse(violations.isEmpty());
    }
}
