package ru.practicum.shareit.bookingTests.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.DbItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.DbUserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private DbItemRepository itemRepository;
    @Autowired
    private DbUserRepository userRepository;

    private Item testItem;
    private User testOwner;
    private User testBooker;
    private Booking testBooking;

    @BeforeEach
    void setUp() {
        testOwner = userRepository.save(Generators.generateUser(1L));
        testBooker = userRepository.save(Generators.generateUser(2L));
        testItem = itemRepository.save(Generators.generateItem(1L, testOwner.getId()));
        testBooking = bookingRepository.save(Generators.generateBooking(testItem, testBooker, BookingStatus.WAITING));
    }

    @Test
    void testSaveBooking() {
        Optional<Booking> booking = bookingRepository.findById(testBooking.getId());

        assertTrue(booking.isPresent());
    }

    @Test
    void testFindByNotExistId() {
        Optional<Booking> booking = bookingRepository.findById(9999L);

        assertFalse(booking.isPresent());
    }

    @Test
    void testFindByBooker() {
        List<Booking> bookings = bookingRepository.findByBookerId(testBooker.getId());

        assertTrue(bookings.size() == 1);

        fillDbForUser();
        List<Booking> bookings2 = bookingRepository.findByBookerId(testBooker.getId());

        assertTrue(bookings2.size() == 5);

    }

    @Test
    void testFindByNotExistBooker() {
        List<Booking> bookings = bookingRepository.findByBookerId(2000L);
        assertTrue(bookings.isEmpty());
    }

    @Test
    void testFindByOwnerId() {
        List<Booking> ownerBookings = bookingRepository.findAllByOwnerId(testOwner.getId());

        assertTrue(ownerBookings.size() == 1);
    }

    @Test
    void testFindByNotOwnerId() {
        List<Booking> bookings = bookingRepository.findAllByOwnerId(testBooker.getId());
        assertTrue(bookings.isEmpty());
    }

    @Test
    void testFindByItemId() {
        List<Booking> bookings = bookingRepository.findByItemId(testItem.getId());
        assertEquals(bookings.getFirst(), testBooking);
    }


    private void fillDbForUser() {
        bookingRepository.save(
                Generators.generateBooking(itemRepository.save(
                        Generators.generateItem(12L, testOwner.getId())), testBooker, BookingStatus.WAITING));
        bookingRepository.save(
                Generators.generateBooking(itemRepository.save(
                        Generators.generateItem(13L, testOwner.getId())), testBooker, BookingStatus.WAITING));
        bookingRepository.save(
                Generators.generateBooking(itemRepository.save(
                        Generators.generateItem(14L, testOwner.getId())), testBooker, BookingStatus.WAITING));
        bookingRepository.save(
                Generators.generateBooking(itemRepository.save(
                        Generators.generateItem(15L, testOwner.getId())), testBooker, BookingStatus.WAITING));
    }
}
