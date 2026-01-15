package ru.practicum.shareit.bookingTests.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.user.dto.UserResponseDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = BookingResponseDto.class)
public class BookingResponseDtoTest {
    @Autowired
    private JacksonTester<BookingResponseDto> json;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");

    @Test
    void testSerialization() throws IOException {
        LocalDateTime start = LocalDateTime.now().plusMinutes(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);

        ItemResponseDto item = new ItemResponseDto(
                1L,
                "Name",
                "Description",
                true
        );

        UserResponseDto booker = new UserResponseDto(
                1L,
                "User",
                "email@ya.ru"
        );

        BookingResponseDto dto = new BookingResponseDto(
                1L,
                item,
                booker,
                start,
                end,
                BookingStatus.APPROVED
        );

        JsonContent<BookingResponseDto> result = json.write(dto);

        // Корневые поля
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.status")
                .isEqualTo("APPROVED");

        // Даты
        assertThat(result).extractingJsonPathStringValue("$.start")
                .isEqualTo(start.format(formatter));
        assertThat(result).extractingJsonPathStringValue("$.end")
                .isEqualTo(end.format(formatter));

        // Item
        assertThat(result).extractingJsonPathNumberValue("$.item.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.item.name").isEqualTo("Name");
        assertThat(result).extractingJsonPathStringValue("$.item.description")
                .isEqualTo("Description");
        assertThat(result).extractingJsonPathBooleanValue("$.item.available")
                .isTrue();

        // Booker
        assertThat(result).extractingJsonPathNumberValue("$.booker.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.booker.name").isEqualTo("User");
        assertThat(result).extractingJsonPathStringValue("$.booker.email")
                .isEqualTo("email@ya.ru");
    }

    @Test
    void testDeserialization() throws IOException {
        LocalDateTime start = LocalDateTime.now().plusMinutes(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);

        String jsonString =
                "{\"id\":1," +
                        "\"item\":{" +
                        "\"id\":1," +
                        "\"name\":\"Name\"," +
                        "\"description\":\"Description\"," +
                        "\"available\":true" +
                        "}," +
                        "\"booker\":{" +
                        "\"id\":1," +
                        "\"name\":\"User\"," +
                        "\"email\":\"email@ya.ru\"" +
                        "}," +
                        "\"start\":\"" + start.format(formatter) + "\"," +
                        "\"end\":\"" + end.format(formatter) + "\"," +
                        "\"status\":\"APPROVED\"}";




        BookingResponseDto deserialized = json.parseObject(jsonString);

        // Проверка корневых полей
        assertThat(deserialized.getId()).isEqualTo(1L);
        assertThat(deserialized.getStart()).isEqualTo(start);
        assertThat(deserialized.getEnd()).isEqualTo(end);
        assertThat(deserialized.getStatus()).isEqualTo(BookingStatus.APPROVED);

        // Проверка item
        assertThat(deserialized.getItem()).isNotNull();
        assertThat(deserialized.getItem().getId()).isEqualTo(1L);
        assertThat(deserialized.getItem().getName()).isEqualTo("Name");
        assertThat(deserialized.getItem().getDescription()).isEqualTo("Description");
        assertThat(deserialized.getItem().getAvailable()).isTrue();

        // Проверка booker
        assertThat(deserialized.getBooker()).isNotNull();
        assertThat(deserialized.getBooker().getId()).isEqualTo(1L);
        assertThat(deserialized.getBooker().getName()).isEqualTo("User");
        assertThat(deserialized.getBooker().getEmail()).isEqualTo("email@ya.ru");
    }
}
