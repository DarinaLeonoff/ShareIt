package ru.practicum.shareit.bookingTests.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.booking.dto.BookingRequestDto;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = BookingRequestDto.class)
public class BookingRequestDtoJsonTest {

    @Autowired
    private JacksonTester<BookingRequestDto> json;

    @Test
    void testSerialization() throws IOException {
        BookingRequestDto dto = BookingRequestDto.builder()
                .itemId(123L)
                .start(LocalDateTime.of(2026, 1, 16, 10, 0))
                .end(LocalDateTime.of(2026, 1, 17, 10, 0))
                .build();

        JsonContent<BookingRequestDto> content = json.write(dto);

        assertThat(content).extractingJsonPathNumberValue("$.itemId").isEqualTo(123);
        assertThat(content).extractingJsonPathStringValue("$.start")
                .isEqualTo("2026-01-16T10:00:00");
        assertThat(content).extractingJsonPathStringValue("$.end")
                .isEqualTo("2026-01-17T10:00:00");
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"itemId\": 456,\n" +
                        "  \"start\": \"2026-02-01T12:00:00\",\n" +
                        "  \"end\": \"2026-02-02T12:00:00\"\n" +
                        "}";

        BookingRequestDto dto = json.parseObject(jsonString);

        assertThat(dto.getItemId()).isEqualTo(456L);
        assertThat(dto.getStart()).isEqualTo(LocalDateTime.of(2026, 2, 1, 12, 0));
        assertThat(dto.getEnd()).isEqualTo(LocalDateTime.of(2026, 2, 2, 12, 0));
    }
}
