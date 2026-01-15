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
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = BookingRequestDto.class)
public class BookingRequestDtoTest {
    @Autowired
    private JacksonTester<BookingRequestDto> json;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");

    @Test
    void testSerialization() throws IOException {
        BookingRequestDto booking = BookingRequestDto.builder()
                .itemId(1L)
                .start(LocalDateTime.now().plusMinutes(1))
                .end(LocalDateTime.now().plusDays(1))
                .build();

        JsonContent<BookingRequestDto> result = json.write(booking);
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
                "\"start\":\"" + start.format(formatter) + "\"," +
                "\"end\":\"" + end.format(formatter) + "\"" +
                "}";

        BookingRequestDto deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getItemId()).isEqualTo(1L);
        assertThat(deserialized.getStart()).isNotNull();
        assertThat(deserialized.getEnd()).isNotNull();
    }
}
