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
public class NewCommentTest {
    @Autowired
    private JacksonTester<NewCommentDto> json;

    @Test
    void testSerialization() throws IOException {
        NewCommentDto comment = NewCommentDto.builder()
                .text("Comment")
                .build();

        JsonContent<NewCommentDto> result = json.write(comment);
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo(comment.getText());
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString = "{\n" +
                "    \"text\": \"Comment\"\n" +
                "}";

        NewCommentDto deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getText()).isEqualTo("Comment");
    }
}
