package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.booking.dto.DateValidationGroup;
import ru.practicum.shareit.constants.Constants;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> makeBooking(@Valid @RequestBody BookItemRequestDto dto,
                                              @RequestHeader(Constants.USER_ID_HEADER) long userId) {
        log.info("Start booking item {}", dto.getItemId());
        return bookingClient.makeBooking(userId, dto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(@RequestHeader(Constants.USER_ID_HEADER) long userId,
                                                 @PathVariable long bookingId,
                                                 @RequestParam boolean approved) {
        log.info("User by id = {} try to approve({}) item with id = {}", userId, approved, bookingId);
        return bookingClient.approveBooking(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingById(@RequestHeader(Constants.USER_ID_HEADER) long userId,
                                                 @PathVariable long bookingId) {
        return bookingClient.getBookingById(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUserBookings(@RequestHeader(Constants.USER_ID_HEADER) long userId,
                                                     @RequestParam(defaultValue = "ALL") BookingState state) {
        log.info("Current state = {}", state);
        return bookingClient.getAllUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllUserItemBooking(@RequestHeader(Constants.USER_ID_HEADER) long userId,
                                                        @RequestParam(defaultValue = "ALL") BookingState state) {
        return bookingClient.getAllUserItemBooking(userId, state);
    }
}