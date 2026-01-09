package ru.practicum.shareit;

import ru.practicum.shareit.booking.dto.BookingDateDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.comment.NewCommentDto;
import ru.practicum.shareit.item.dto.item.ItemRequestDto;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.item.dto.item.ItemWithCommentAndBookingDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.RequestItemDto;
import ru.practicum.shareit.request.dto.RequestItemResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Random;

public class Generators {
    private static final Random random = new Random();

    //models
    public static Item generateItem(Long id) {
        return Item.builder()
                .id(id)
                .name("Test")
                .description("Desc")
                .available(true)
                .ownerId(100L)
                .build();
    }

    public static Item generateItem(Long id, Long userId) {
        return Item.builder()
                .name("Test " + id)
                .description("Desc")
                .available(true)
                .ownerId(userId)
                .build();
    }

    public static User generateUser(Long i) {
        User user = new User();
        user.setId(i);
        user.setName("Test Name " + i);
        user.setEmail("test" + i + "@ya.ru");
        return user;
    }

    public static Booking generateBooking(Long i, BookingStatus s) {
        return Booking.builder()
                .id(i)
                .item(generateItem(1L))
                .booker(generateUser(1L))
                .start(LocalDateTime.now().minusDays(1))
                .end(LocalDateTime.now())
                .status(s)
                .build();
    }

    public static Booking generateBooking(Item item, User user, BookingStatus s) {
        return Booking.builder()
                .item(item)
                .booker(user)
                .start(LocalDateTime.now().minusDays(1))
                .end(LocalDateTime.now())
                .status(s)
                .build();
    }

    public static Comment generateComment(User user, Item item) {
        return Comment.builder()
                .text("Comment")
                .user(user)
                .item(item)
                .created(LocalDateTime.now())
                .build();
    }

    //dtos
    public static UserRequestDto generateUserRequestDto() {
        return UserRequestDto.builder()
                .name("Test Name " + LocalDateTime.now().getSecond() + random.nextInt())
                .email("TestEmail" + random.nextInt() + "@ya.ru")
                .build();
    }

    public static UserResponseDto generateUserResponseDto(long id) {
        return UserResponseDto.builder()
                .id(id)
                .name("Test Name " + LocalDateTime.now().getSecond())
                .email("TestEmail@ya.ru")
                .build();
    }

    public static ItemRequestDto generateItemRequest() {
        return ItemRequestDto.builder()
                .name("Item")
                .description("Description")
                .available(true)
                .build();
    }

    public static ItemResponseDto generateItemResponse(long id) {
        return ItemResponseDto.builder()
                .id(id)
                .name("Item")
                .description("Description")
                .available(true)
                .build();
    }

    public static ItemWithCommentAndBookingDto generateItemWithCommentAndBooking(long id) {
        return ItemWithCommentAndBookingDto.builder()
                .id(id)
                .name("Item")
                .description("Description")
                .available(true)
                .build();
    }

    public static NewCommentDto generateNewComment() {
        return NewCommentDto.builder()
                .text("New Comment")
                .build();
    }

    public static CommentResponseDto generateCommentResponse() {
        return CommentResponseDto.builder()
                .text("Comment text")
                .authorName("Author")
                .created(LocalDateTime.now())
                .build();
    }

    public static BookingDateDto generateBookingDateDto() {
        return BookingDateDto.builder()
                .start(LocalDateTime.now().minusDays(1))
                .end(LocalDateTime.now())
                .build();
    }

    public static BookingResponseDto generateBookingResponse() {
        return BookingResponseDto.builder()
                .id(1L)
                .item(generateItemResponse(1L))
                .booker(generateUserResponseDto(1L))
                .start(LocalDateTime.now().minusDays(2))
                .end(LocalDateTime.now().minusDays(1))
                .status(BookingStatus.APPROVED)
                .build();
    }

    public static BookingRequestDto generateBookingRequest(long itemId) {
        return BookingRequestDto.builder()
                .itemId(itemId)
                .start(LocalDateTime.now().minusDays(2))
                .end(LocalDateTime.now().minusDays(1))
                .build();
    }

    public static ItemRequest generateRequestItem(User user) {
        return ItemRequest.builder()
                .user(user)
                .description("test " + random.nextInt())
                .created(LocalDateTime.now())
                .build();
    }

    public static RequestItemDto generateRequestItemDto() {
        return RequestItemDto.builder().description("test " + random.nextInt()).build();
    }

    public static RequestItemResponseDto genereteRequestResponse() {
        return RequestItemResponseDto.builder().id(1L).description("test " + random.nextInt()).created(LocalDateTime.now()).build();
    }

}
