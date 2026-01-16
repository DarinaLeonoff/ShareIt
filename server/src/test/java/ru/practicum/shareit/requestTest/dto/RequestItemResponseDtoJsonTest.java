package ru.practicum.shareit.requestTest.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.item.dto.item.AnswerDto;
import ru.practicum.shareit.request.dto.RequestItemResponseDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = {RequestItemResponseDto.class})
public class RequestItemResponseDtoJsonTest {

    @Autowired
    private JacksonTester<RequestItemResponseDto> json;

    @Test
    void testSerialization() throws IOException {
        AnswerDto answer1 = AnswerDto.builder()
                .id(1L)
                .name("Отвечающий")
                .ownerId(1L)
                .build();

        RequestItemResponseDto dto = RequestItemResponseDto.builder()
                .id(10L)
                .description("Нужна лестница")
                .created(LocalDateTime.of(2026, 1, 16, 10, 0))
                .items(List.of(answer1))
                .build();

        JsonContent<RequestItemResponseDto> content = json.write(dto);

        assertThat(content).extractingJsonPathNumberValue("$.id").isEqualTo(10);
        assertThat(content).extractingJsonPathStringValue("$.description").isEqualTo("Нужна лестница");
        assertThat(content).extractingJsonPathStringValue("$.created").isEqualTo("2026-01-16T10:00:00");
        assertThat(content).extractingJsonPathNumberValue("$.items[0].id").isEqualTo(1);
        assertThat(content).extractingJsonPathStringValue("$.items[0].name").isEqualTo("Отвечающий");
        assertThat(content).extractingJsonPathNumberValue("$.items[0].ownerId").isEqualTo(1);
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"id\": 20,\n" +
                        "  \"description\": \"Ищу перфоратор\",\n" +
                        "  \"created\": \"2026-02-01T12:00:00\",\n" +
                        "  \"items\": [\n" +
                        "    {\n" +
                        "      \"id\": 2,\n" +
                        "      \"name\": \"Ответчик\",\n" +
                        "      \"ownerId\": 1\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";

        RequestItemResponseDto dto = json.parseObject(jsonString);

        assertThat(dto.getId()).isEqualTo(20L);
        assertThat(dto.getDescription()).isEqualTo("Ищу перфоратор");
        assertThat(dto.getCreated()).isEqualTo(LocalDateTime.of(2026, 2, 1, 12, 0));
        assertThat(dto.getItems()).hasSize(1);
        assertThat(dto.getItems().get(0).getId()).isEqualTo(2L);
        assertThat(dto.getItems().get(0).getName()).isEqualTo("Ответчик");
        assertThat(dto.getItems().get(0).getOwnerId()).isEqualTo(1);
    }
}

