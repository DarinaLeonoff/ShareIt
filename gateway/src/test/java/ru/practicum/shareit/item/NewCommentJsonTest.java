package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.item.dto.NewComment;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@Import(NewComment.class)
public class NewCommentJsonTest {

    @Autowired
    private JacksonTester<NewComment> json;

    @Test
    void testSerialization() throws IOException {
        NewComment comment = new NewComment();
        comment.setText("Отличная вещь");

        JsonContent<NewComment> content = json.write(comment);

        assertThat(content).extractingJsonPathStringValue("$.text")
                .isEqualTo("Отличная вещь");
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"text\": \"Очень удобно использовать\"\n" +
                        "}";

        NewComment comment = json.parseObject(jsonString);

        assertThat(comment.getText())
                .isEqualTo("Очень удобно использовать");
    }
}

