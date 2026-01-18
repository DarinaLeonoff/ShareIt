package ru.practicum.shareit.itemTests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.item.dto.item.ItemWithCommentAndBookingDto;
import ru.practicum.shareit.item.repository.DbItemRepository;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.repository.DbUserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class ItemServiceCommentsTest {

    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BookingServiceImpl bookingService;

    @Mock
    private DbUserRepository userRepository;
    @Mock
    private DbItemRepository itemRepository;
    @Mock
    private BookingRepository bookingRepository;

    private ItemResponseDto item;
    private UserResponseDto owner;
    private UserResponseDto booker;
    private BookingResponseDto booking;
    private CommentResponseDto comment;

    @BeforeEach
    void setUp() {
        owner = userService.createUser(Generators.generateUserRequestDto());
        booker = userService.createUser(Generators.generateUserRequestDto());
        item = itemService.createItem(owner.getId(), Generators.generateItemRequest());
        booking = bookingService.makeBooking(Generators.generateBookingRequest(item.getId()), booker.getId());
        bookingService.approveBooking(owner.getId(), booking.getId(), true);
        comment = itemService.addComment(booker.getId(), item.getId(), Generators.generateNewComment());
    }

    @Test
    void addCommentTest() {
        CommentResponseDto getComment = itemService.getItemWithCommentById(booker.getId(), item.getId()).getComments().get(0);

        assertEquals(comment, getComment);
    }

    @Test
    void getItemWithCommentAndBookingByBooker() {
        ItemWithCommentAndBookingDto fullItem = itemService.getItemWithCommentById(booker.getId(), item.getId());

        assertNull(fullItem.getLastBooking());
        assertNull(fullItem.getNextBooking());
    }

    @Test
    void getItemWithCommentAndBookingByOwner() {
        ItemWithCommentAndBookingDto fullItem = itemService.getItemWithCommentById(owner.getId(), item.getId());

        assertEquals(booking.getStart(), fullItem.getLastBooking().getStart());
        assertEquals(booking.getEnd(), fullItem.getLastBooking().getEnd());
    }

    @Test
    void getAllUserItemsTets() {
        List<ItemWithCommentAndBookingDto> res = itemService.getAllUserItems(owner.getId());

        assertEquals(1, res.size());
        assertEquals(item.getId(), res.get(0).getId());
    }

}
