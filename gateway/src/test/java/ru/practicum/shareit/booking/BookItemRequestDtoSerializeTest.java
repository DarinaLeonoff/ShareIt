package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
@Import({BookItemRequestDto.class})
public class BookItemRequestDtoSerializeTest {
    @Autowired
    private JacksonTester<BookItemRequestDto> json;

    @Test
    void testSerialization() throws IOException {
        BookItemRequestDto dto = BookItemRequestDto.builder()
                .itemId(123L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusDays(7))
                .build();

        JsonContent<BookItemRequestDto> content = json.write(dto);

        assertThat(content).extractingJsonPathNumberValue("$.itemId").isEqualTo(123);
        assertThat(content).extractingJsonPathStringValue("$.start").isNotNull();
        assertThat(content).extractingJsonPathStringValue("$.end").isNotNull();
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\"itemId\":456,\n" +
                        "\"start\": \"2026-01-16T16:20:00\",\n" +
                        "\"end\":\"2026-01-23T16:20:00\"\n" +
                        "}";

        BookItemRequestDto dto = json.parseObject(jsonString);

        assertThat(dto.getItemId()).isEqualTo(456L);
        assertThat(dto.getStart()).isNotNull();
        assertThat(dto.getEnd()).isNotNull();
        assertThat(dto.isValidDateRange()).isTrue();
    }
}
