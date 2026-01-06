package ru.practicum.shareit.BookingTests.service;

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
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.booking.service.BookingState;
import ru.practicum.shareit.exceptions.WrongRequestException;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.item.repository.DbItemRepository;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.repository.DbUserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

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
    void testMakeAndGetBooking() {
        BookingResponseDto addedBooking = bookingService.getBookingById(booker.getId(), booking.getId());

        assertEquals(booking, addedBooking);
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
    void getAllUserBookingTest() {
        bookingService.approveBooking(owner.getId(), booking.getId(), true);
        List<BookingResponseDto> allBookings = bookingService.getAllUserBooking(booker.getId(), BookingState.ALL);
        List<BookingResponseDto> pastBookings = bookingService.getAllUserBooking(booker.getId(), BookingState.PAST);

        assertEquals(1, allBookings.size(), "All bookings size is not 1");
        assertEquals(1, pastBookings.size(), "Past bookings size is not 1");
        assertEquals(allBookings.getFirst(), pastBookings.getFirst(), "Not the same booking in lists");
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
}
