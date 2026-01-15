package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookItemRequestDtoSerializationTest {
    private final JacksonTester<BookItemRequestDto> json;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");

    @Test
    void testSerialization() throws IOException {
        LocalDateTime start = LocalDateTime.now().plusMinutes(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        BookItemRequestDto booking = BookItemRequestDto.builder()
                .itemId(1L)
                .start(start)
                .end(end)
                .build();

        JsonContent<BookItemRequestDto> result = json.write(booking);
        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(1);
        assertThat(result)
                .extractingJsonPathStringValue("$.start")
                .isEqualTo(booking.getStart().format(formatter));
        assertThat(result)
                .extractingJsonPathStringValue("$.end")
                .isEqualTo(booking.getEnd().format(formatter));
    }

    @Test
    void testDeserialization() throws IOException {
        LocalDateTime start = LocalDateTime.now().plusMinutes(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        String jsonString = "{\n" +
                "    \"itemId\": 1,\n" +
                "    \"start\": \"" + start.format(formatter) + "\",\n" +
                "    \"end\": \"" + end.format(formatter) + "\"\n" +
                "}";

        BookItemRequestDto deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getItemId()).isEqualTo(1L);
        assertThat(deserialized.getStart()).isNotNull();
        assertThat(deserialized.getEnd()).isNotNull();
    }
}
