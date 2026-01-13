package ru.practicum.shareit.bookingTests.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.booking.service.BookingState;
import ru.practicum.shareit.exceptions.WrongRequestException;
import ru.practicum.shareit.item.dto.item.ItemRequestDto;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.item.repository.DbItemRepository;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.repository.DbUserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class BookingServiceTest {
    @Autowired
    private BookingServiceImpl bookingService;
    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private UserServiceImpl userService;

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private DbItemRepository itemRepository;
    @Mock
    private DbUserRepository userRepository;

    private ItemResponseDto item;
    private UserResponseDto owner;
    private UserResponseDto booker;
    private BookingResponseDto booking;

    @BeforeEach
    void setUp() {
        owner = userService.createUser(Generators.generateUserRequestDto());
        booker = userService.createUser(Generators.generateUserRequestDto());
        item = itemService.createItem(owner.getId(), Generators.generateItemRequest());
        booking = bookingService.makeBooking(Generators.generateBookingRequest(item.getId()), booker.getId());
    }


    @Test
    void testMakeBooking() {
        BookingRequestDto request = BookingRequestDto.builder()
                .itemId(item.getId())
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(1))
                .build();
        BookingResponseDto saved = bookingService.makeBooking(request, booker.getId());

        assertEquals(request.getItemId(), saved.getItem().getId());
        assertEquals(request.getStart(), saved.getStart());
        assertEquals(request.getEnd(), saved.getEnd());
        assertEquals(booker.getId(), saved.getBooker().getId());
    }

    @Test
    void testMakeBookingByOwner() {
        BookingRequestDto request = BookingRequestDto.builder()
                .itemId(item.getId())
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(1))
                .build();
        assertThrows(WrongRequestException.class, () -> bookingService.makeBooking(request, owner.getId()));
    }

    @Test
    void testMakeBookingOfUnableItem() {
        itemService.editItem(owner.getId(), ItemRequestDto.builder().available(false).build(), item.getId());
        BookingRequestDto request = BookingRequestDto.builder()
                .itemId(item.getId())
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(1))
                .build();
        assertThrows(WrongRequestException.class, () -> bookingService.makeBooking(request, booker.getId()));
    }

    @Test
    void testGetBookingByBooker() {
        BookingResponseDto dto = bookingService.getBookingById(booker.getId(), booking.getId());
        assertEquals(booking, dto);
    }

    @Test
    void testGetBookingByOwner() {
        BookingResponseDto dto = bookingService.getBookingById(owner.getId(), booking.getId());
        assertEquals(booking, dto);
    }

    @Test
    void testGetBookingByWrongUser() {
        UserResponseDto newUser = userService.createUser(UserRequestDto.builder().name("new").email("new@ya.ru").build());
        assertThrows(WrongRequestException.class, () -> {
            bookingService.getBookingById(newUser.getId(), booking.getId());
        });
    }


    @Test
    void testApproveBooking() {
        BookingResponseDto approved = bookingService.approveBooking(owner.getId(), booking.getId(), true);
        assertEquals(booking.getId(), approved.getId());
        assertEquals(approved.getStatus(), BookingStatus.APPROVED);
    }

    @Test
    void testApproveBookingWrongUser() {
        assertThrows(WrongRequestException.class, () -> {
            bookingService.approveBooking(booker.getId(), booking.getId(), true);
        });
    }

    @Test
    void testNotApproveBooking() {
        BookingResponseDto approved = bookingService.approveBooking(owner.getId(), booking.getId(), false);
        assertEquals(booking.getId(), approved.getId());
        assertEquals(approved.getStatus(), BookingStatus.REJECTED);
    }

    @Test
    void getBookingByIdNotOwner() {
        UserResponseDto user = userService.createUser(Generators.generateUserRequestDto());
        assertThrows(WrongRequestException.class, () -> {
            bookingService.getBookingById(user.getId(), booking.getId());
        });
    }

    @Test
    void getAllUserItemBookingTest() {
        bookingService.approveBooking(owner.getId(), booking.getId(), true);
        List<BookingResponseDto> allBookings = bookingService.getAllUserItemBooking(owner.getId(), BookingState.ALL);
        List<BookingResponseDto> pastBookings = bookingService.getAllUserItemBooking(owner.getId(), BookingState.PAST);

        assertEquals(1, allBookings.size(), "All bookings size is not 1");
        assertEquals(1, pastBookings.size(), "Past bookings size is not 1");
        assertEquals(allBookings.getFirst(), pastBookings.getFirst(), "Not the same booking in lists");
    }

    @Test
    void getAllUserBookingTest() {
        bookingService.approveBooking(owner.getId(), booking.getId(), true);
        List<BookingResponseDto> allBookings = bookingService.getAllUserBooking(booker.getId(), BookingState.ALL);
        List<BookingResponseDto> pastBookings = bookingService.getAllUserBooking(booker.getId(), BookingState.PAST);

        assertEquals(1, allBookings.size(), "All bookings size is not 1");
        assertEquals(1, pastBookings.size(), "Past bookings size is not 1");
        assertEquals(allBookings.getFirst(), pastBookings.getFirst(), "Not the same booking in lists");
    }

    @Test
    void getFutureUserBookingTest() {
        BookingResponseDto booking1 = bookingService.makeBooking(BookingRequestDto.builder()
                        .itemId(item.getId()).start(LocalDateTime.now().plusDays(3)).end(LocalDateTime.now().plusDays(4)).build(),
                booker.getId());
        bookingService.approveBooking(owner.getId(), booking1.getId(), true);
        List<BookingResponseDto> allBookings = bookingService.getAllUserBooking(booker.getId(), BookingState.ALL);
        List<BookingResponseDto> futureBookings = bookingService.getAllUserBooking(booker.getId(), BookingState.FUTURE);

        assertEquals(2, allBookings.size(), "All bookings size is not 1");
        assertEquals(1, futureBookings.size(), "Future bookings size is 1");
        assertEquals(booking1.getId(), futureBookings.getFirst().getId(), "Not the same booking in lists");
    }

    @Test
    void getCurrentUserBookingTest() {
        BookingResponseDto booking1 = bookingService.makeBooking(BookingRequestDto.builder()
                        .itemId(item.getId()).start(LocalDateTime.now().minusDays(3)).end(LocalDateTime.now().plusDays(2)).build(),
                booker.getId());
        bookingService.approveBooking(owner.getId(), booking1.getId(), true);
        List<BookingResponseDto> allBookings = bookingService.getAllUserBooking(booker.getId(), BookingState.ALL);
        List<BookingResponseDto> currentBookings = bookingService.getAllUserBooking(booker.getId(), BookingState.CURRENT);

        assertEquals(2, allBookings.size(), "All bookings size is not 1");
        assertEquals(1, currentBookings.size(), "Current bookings size is 1");
        assertEquals(booking1.getId(), currentBookings.getFirst().getId(), "Not the same booking in lists");
    }

    @Test
    void getWaitingUserBookingTest() {
        BookingResponseDto booking1 = bookingService.makeBooking(BookingRequestDto.builder()
                        .itemId(item.getId()).start(LocalDateTime.now().minusDays(3)).end(LocalDateTime.now().plusDays(2)).build(),
                booker.getId());
        bookingService.approveBooking(owner.getId(), booking1.getId(), false);
        List<BookingResponseDto> allBookings = bookingService.getAllUserBooking(booker.getId(), BookingState.ALL);
        List<BookingResponseDto> waitingBookings = bookingService.getAllUserBooking(booker.getId(), BookingState.WAITING);
        List<BookingResponseDto> rejectedBookings = bookingService.getAllUserBooking(booker.getId(), BookingState.REJECTED);

        assertEquals(2, allBookings.size(), "All bookings size is not 1");
        assertEquals(1, waitingBookings.size(), "Waiting bookings size is 1");
        assertEquals(1, rejectedBookings.size(), "Rejected bookings size is 1");

        assertEquals(booking.getId(), waitingBookings.getFirst().getId(), "Not the same booking in lists");
        assertEquals(booking1.getId(), rejectedBookings.getFirst().getId(), "Not the same booking in lists");
    }

}
