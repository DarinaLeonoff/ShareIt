package ru.practicum.shareit.itemTests.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookingDateDto;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.item.AnswerDto;
import ru.practicum.shareit.item.dto.item.ItemRequestDto;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.item.dto.item.ItemWithCommentAndBookingDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ItemMapperTest {
    @Autowired
    private ItemMapper itemMapper;

    private Item item;

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .id(1L)
                .name("Title")
                .description("Description")
                .available(true)
                .ownerId(1L)
                .requestId(1L)
                .build();
    }

    @Test
    void convertItemsToResponsesTest() {
        List<Item> list = List.of(item);
        ItemRequestDto expected = ItemRequestDto.builder()
                .name("Title")
                .description("Description")
                .available(true)
                .requestId(1L)
                .build();

        ItemResponseDto result = itemMapper.mapItemsToResponses(list).get(0);

        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getAvailable(), result.getAvailable());
    }

    @Test
    void convertRequestToItemTest() {
        ItemRequestDto dto = ItemRequestDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(true)
                .requestId(1L)
                .build();

        Item result = itemMapper.mapRequestToItem(dto, 1L);

        assertEquals(item.getName(), result.getName());
        assertEquals(item.getDescription(), result.getDescription());
        assertEquals(item.isAvailable(), result.isAvailable());
        assertEquals(item.getRequestId(), result.getRequestId());
        assertEquals(item.getOwnerId(), result.getOwnerId());
    }

    @Test
    void convertItemToResponseTest() {
        ItemResponseDto expected = ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.isAvailable())
                .build();

        ItemResponseDto result = itemMapper.mapItemToResponse(item);

        assertEquals(expected, result);
    }

    @Test
    void convertItemToItemWithBookingAndCommentsTest() {
        BookingDateDto last = BookingDateDto.builder()
                .id(1L)
                .start(LocalDateTime.now().minusDays(5))
                .end(LocalDateTime.now().minusDays(4))
                .build();
        BookingDateDto next = BookingDateDto.builder()
                .id(2L)
                .start(LocalDateTime.now().plusDays(4))
                .end(LocalDateTime.now().plusDays(5))
                .build();
        List<CommentResponseDto> comments = List.of(CommentResponseDto.builder()
                .id(1L)
                .text("Comment")
                .authorName("Name")
                .created(LocalDateTime.now())
                .build());

        ItemWithCommentAndBookingDto result = itemMapper.mapItemToItemWithBooking(item, last, next, comments);

        assertEquals(result.getLastBooking(), last);
        assertEquals(result.getNextBooking(), next);
        assertEquals(result.getComments(), comments);
        assertEquals(result.getId(), item.getId());
    }

    @Test
    void convertItemToAnswerTest() {
        AnswerDto answer = AnswerDto.builder()
                .id(item.getId())
                .name(item.getName())
                .ownerId(item.getOwnerId())
                .build();

        AnswerDto result = itemMapper.mapItemToAnswer(item);
        assertEquals(answer, result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void updateItemDescriptionTest(String name) {
        ItemRequestDto updates = ItemRequestDto.builder()
                .name(name)
                .description("Description")
                .build();

        Item result = itemMapper.updateItem(item, updates);

        assertEquals(item.getId(), result.getId());
        assertEquals(item.getName(), result.getName());
        assertEquals(updates.getDescription(), result.getDescription());
        assertEquals(item.isAvailable(), result.isAvailable());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void updateItemNameTest(String description) {
        ItemRequestDto updates = ItemRequestDto.builder()
                .name("New name")
                .description(description)
                .build();

        Item result = itemMapper.updateItem(item, updates);

        assertEquals(item.getId(), result.getId());
        assertEquals(updates.getName(), result.getName());
        assertEquals(item.getDescription(), result.getDescription());
        assertEquals(item.isAvailable(), result.isAvailable());
    }

    @Test
    void updateItemAvailableTest() {
        ItemRequestDto updates = ItemRequestDto.builder()
                .name(null)
                .description(null)
                .available(false)
                .build();

        Item result = itemMapper.updateItem(item, updates);

        assertEquals(item.getId(), result.getId());
        assertEquals(item.getName(), result.getName());
        assertEquals(item.getDescription(), result.getDescription());
        assertEquals(updates.getAvailable(), result.isAvailable());
    }
}
