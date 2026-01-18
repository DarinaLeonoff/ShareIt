package ru.practicum.shareit.itemTests.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.item.dto.comment.NewCommentDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = NewCommentDto.class)
public class NewCommentDtoJsonTest {

    @Autowired
    private JacksonTester<NewCommentDto> json;

    @Test
    void testSerialization() throws IOException {
        NewCommentDto comment = new NewCommentDto();
        comment.setText("Отличная вещь");

        JsonContent<NewCommentDto> content = json.write(comment);

        assertThat(content).extractingJsonPathStringValue("$.text")
                .isEqualTo("Отличная вещь");
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"text\": \"Очень удобно использовать\"\n" +
                        "}";

        NewCommentDto comment = json.parseObject(jsonString);

        assertThat(comment.getText())
                .isEqualTo("Очень удобно использовать");
    }
}
