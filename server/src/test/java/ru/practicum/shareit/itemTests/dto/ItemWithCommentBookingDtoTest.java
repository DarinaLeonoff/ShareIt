package ru.practicum.shareit.itemTests.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.booking.dto.BookingDateDto;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.item.ItemWithCommentAndBookingDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = ItemWithCommentAndBookingDto.class)
public class ItemWithCommentBookingDtoTest {
    @Autowired
    private JacksonTester<ItemWithCommentAndBookingDto> json;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


    @Test
    void testItemWithCommentAndBookingSerialization() throws IOException {
        LocalDateTime bookingStart = LocalDateTime.of(2026, 1, 10, 10, 0);
        LocalDateTime bookingEnd = LocalDateTime.of(2026, 1, 11, 10, 0);
        LocalDateTime commentCreated = LocalDateTime.of(2026, 1, 5, 12, 30);

        BookingDateDto lastBooking = BookingDateDto.builder()
                .id(10L)
                .start(bookingStart)
                .end(bookingEnd)
                .build();

        BookingDateDto nextBooking = BookingDateDto.builder()
                .id(11L)
                .start(bookingStart.plusDays(5))
                .end(bookingEnd.plusDays(5))
                .build();

        CommentResponseDto comment = CommentResponseDto.builder()
                .id(100L)
                .text("Nice item")
                .authorName("User")
                .created(commentCreated)
                .build();

        ItemWithCommentAndBookingDto dto = ItemWithCommentAndBookingDto.builder()
                .id(1L)
                .name("Item name")
                .description("Item description")
                .available(true)
                .lastBooking(lastBooking)
                .nextBooking(nextBooking)
                .comments(List.of(comment))
                .build();

        JsonContent<ItemWithCommentAndBookingDto> result = json.write(dto);

        // основные поля
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name")
                .isEqualTo("Item name");
        assertThat(result).extractingJsonPathStringValue("$.description")
                .isEqualTo("Item description");
        assertThat(result).extractingJsonPathBooleanValue("$.available")
                .isTrue();

        // lastBooking
        assertThat(result).extractingJsonPathNumberValue("$.lastBooking.id")
                .isEqualTo(10);
        assertThat(result).extractingJsonPathStringValue("$.lastBooking.start")
                .isEqualTo(bookingStart.format(formatter));
        assertThat(result).extractingJsonPathStringValue("$.lastBooking.end")
                .isEqualTo(bookingEnd.format(formatter));

        // nextBooking
        assertThat(result).extractingJsonPathNumberValue("$.nextBooking.id")
                .isEqualTo(11);
        assertThat(result).extractingJsonPathStringValue("$.nextBooking.start")
                .isEqualTo(bookingStart.plusDays(5).format(formatter));
        assertThat(result).extractingJsonPathStringValue("$.nextBooking.end")
                .isEqualTo(bookingEnd.plusDays(5).format(formatter));

        // comments
        assertThat(result).extractingJsonPathNumberValue("$.comments[0].id")
                .isEqualTo(100);
        assertThat(result).extractingJsonPathStringValue("$.comments[0].text")
                .isEqualTo("Nice item");
        assertThat(result).extractingJsonPathStringValue("$.comments[0].authorName")
                .isEqualTo("User");
        assertThat(result).extractingJsonPathStringValue("$.comments[0].created")
                .isEqualTo(commentCreated.format(formatter));
    }

    @Test
    void testItemWithCommentAndBookingDeserialization() throws IOException {
        LocalDateTime bookingStart = LocalDateTime.of(2026, 1, 10, 10, 0);
        LocalDateTime bookingEnd = LocalDateTime.of(2026, 1, 11, 10, 0);
        LocalDateTime commentCreated = LocalDateTime.of(2026, 1, 5, 12, 30);

        String jsonString =
                "{\"id\":1," +
                        "\"name\":\"Item name\"," +
                        "\"description\":\"Item description\"," +
                        "\"available\":true," +
                        "\"lastBooking\":{" +
                        "\"id\":10," +
                        "\"start\":\"" + bookingStart.format(formatter) + "\"," +
                        "\"end\":\"" + bookingEnd.format(formatter) + "\"" +
                        "}," +
                        "\"nextBooking\":{" +
                        "\"id\":11," +
                        "\"start\":\"" + bookingStart.plusDays(5).format(formatter) + "\"," +
                        "\"end\":\"" + bookingEnd.plusDays(5).format(formatter) + "\"" +
                        "}," +
                        "\"comments\":[" +
                        "{" +
                        "\"id\":100," +
                        "\"text\":\"Nice item\"," +
                        "\"authorName\":\"User\"," +
                        "\"created\":\"" + commentCreated.format(formatter) + "\"" +
                        "}" +
                        "]}";


        ItemWithCommentAndBookingDto dto = json.parseObject(jsonString);

        // основные поля
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Item name");
        assertThat(dto.getDescription()).isEqualTo("Item description");
        assertThat(dto.getAvailable()).isTrue();

        // lastBooking
        assertThat(dto.getLastBooking()).isNotNull();
        assertThat(dto.getLastBooking().getId()).isEqualTo(10L);
        assertThat(dto.getLastBooking().getStart()).isEqualTo(bookingStart);
        assertThat(dto.getLastBooking().getEnd()).isEqualTo(bookingEnd);

        // nextBooking
        assertThat(dto.getNextBooking()).isNotNull();
        assertThat(dto.getNextBooking().getId()).isEqualTo(11L);
        assertThat(dto.getNextBooking().getStart())
                .isEqualTo(bookingStart.plusDays(5));
        assertThat(dto.getNextBooking().getEnd())
                .isEqualTo(bookingEnd.plusDays(5));

        // comments
        assertThat(dto.getComments()).hasSize(1);

        CommentResponseDto comment = dto.getComments().get(0);
        assertThat(comment.getId()).isEqualTo(100L);
        assertThat(comment.getText()).isEqualTo("Nice item");
        assertThat(comment.getAuthorName()).isEqualTo("User");
        assertThat(comment.getCreated()).isEqualTo(commentCreated);
    }

}
