package ru.practicum.shareit.itemTests.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = CommentResponseDto.class)
public class CommentResponseDtoJsonTest {

    @Autowired
    private JacksonTester<CommentResponseDto> json;

    @Test
    void testSerialization() throws IOException {
        CommentResponseDto dto = CommentResponseDto.builder()
                .id(1L)
                .text("Отличная вещь")
                .authorName("Дарья")
                .created(LocalDateTime.of(2026, 1, 16, 15, 30))
                .build();

        JsonContent<CommentResponseDto> content = json.write(dto);

        assertThat(content).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(content).extractingJsonPathStringValue("$.text").isEqualTo("Отличная вещь");
        assertThat(content).extractingJsonPathStringValue("$.authorName").isEqualTo("Дарья");
        assertThat(content).extractingJsonPathStringValue("$.created").isEqualTo("2026-01-16T15:30:00");
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"id\": 2,\n" +
                        "  \"text\": \"Очень удобно\",\n" +
                        "  \"authorName\": \"Алексей\",\n" +
                        "  \"created\": \"2026-02-01T12:45:00\"\n" +
                        "}";

        CommentResponseDto dto = json.parseObject(jsonString);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getText()).isEqualTo("Очень удобно");
        assertThat(dto.getAuthorName()).isEqualTo("Алексей");
        assertThat(dto.getCreated()).isEqualTo(LocalDateTime.of(2026, 2, 1, 12, 45));
    }
}

