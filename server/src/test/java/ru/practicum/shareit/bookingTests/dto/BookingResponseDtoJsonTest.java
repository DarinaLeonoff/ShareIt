package ru.practicum.shareit.bookingTests.dto;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.user.dto.UserResponseDto;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = BookingResponseDto.class)
public class BookingResponseDtoJsonTest {

    @Autowired
    private JacksonTester<BookingResponseDto> json;

    @Test
    void testSerialization() throws IOException {
        BookingResponseDto dto = getBookingResponseDto();

        JsonContent<BookingResponseDto> content = json.write(dto);

        assertThat(content).extractingJsonPathNumberValue("$.id")
                .isEqualTo(10);

        assertThat(content).extractingJsonPathNumberValue("$.item.id")
                .isEqualTo(1);
        assertThat(content).extractingJsonPathStringValue("$.item.name")
                .isEqualTo("Дрель");

        assertThat(content).extractingJsonPathNumberValue("$.booker.id")
                .isEqualTo(2);
        assertThat(content).extractingJsonPathStringValue("$.booker.name")
                .isEqualTo("Дарья");
        assertThat(content).extractingJsonPathStringValue("$.booker.email")
                .isEqualTo("darya@mail.ru");

        assertThat(content).extractingJsonPathStringValue("$.start")
                .isEqualTo("2026-01-16T10:00:00");
        assertThat(content).extractingJsonPathStringValue("$.end")
                .isEqualTo("2026-01-17T10:00:00");

        assertThat(content).extractingJsonPathStringValue("$.status")
                .isEqualTo("APPROVED");
    }

    private static @NonNull BookingResponseDto getBookingResponseDto() {
        ItemResponseDto item = new ItemResponseDto();
        item.setId(1L);
        item.setName("Дрель");

        UserResponseDto booker = new UserResponseDto();
        booker.setId(2L);
        booker.setName("Дарья");
        booker.setEmail("darya@mail.ru");

        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(10L);
        dto.setItem(item);
        dto.setBooker(booker);
        dto.setStart(LocalDateTime.of(2026, 1, 16, 10, 0));
        dto.setEnd(LocalDateTime.of(2026, 1, 17, 10, 0));
        dto.setStatus(BookingStatus.APPROVED);
        return dto;
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"id\": 20,\n" +
                        "  \"item\": {\n" +
                        "    \"id\": 3,\n" +
                        "    \"name\": \"Молоток\"\n" +
                        "  },\n" +
                        "  \"booker\": {\n" +
                        "    \"id\": 4,\n" +
                        "    \"name\": \"Алексей\",\n" +
                        "    \"email\": \"alex@mail.ru\"\n" +
                        "  },\n" +
                        "  \"start\": \"2026-02-01T12:00:00\",\n" +
                        "  \"end\": \"2026-02-02T12:00:00\",\n" +
                        "  \"status\": \"WAITING\"\n" +
                        "}";

        BookingResponseDto dto = json.parseObject(jsonString);

        assertThat(dto.getId()).isEqualTo(20L);
        assertThat(dto.getItem().getId()).isEqualTo(3L);
        assertThat(dto.getItem().getName()).isEqualTo("Молоток");
        assertThat(dto.getBooker().getId()).isEqualTo(4L);
        assertThat(dto.getBooker().getName()).isEqualTo("Алексей");
        assertThat(dto.getBooker().getEmail()).isEqualTo("alex@mail.ru");
        assertThat(dto.getStart()).isEqualTo(LocalDateTime.of(2026, 2, 1, 12, 0));
        assertThat(dto.getEnd()).isEqualTo(LocalDateTime.of(2026, 2, 2, 12, 0));
        assertThat(dto.getStatus()).isEqualTo(BookingStatus.WAITING);
    }
}