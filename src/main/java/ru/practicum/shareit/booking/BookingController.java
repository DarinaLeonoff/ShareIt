package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.constants.Constants;

/**
 * TODO Sprint add-bookings.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingResponseDto makeBooking(@RequestBody BookingRequestDto dto,
                                              @RequestHeader(Constants.USER_ID_HEADER) long userId){
        return bookingService.makeBooking(dto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponseDto approveBooking(@RequestHeader(Constants.USER_ID_HEADER) long userId, @PathVariable long bookingId, @RequestParam boolean approved){
        log.info("User by id = {} try to approve({}) item with id = {}", userId, approved, bookingId);
        return bookingService.approveBooking(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingResponseDto getBookingById(@RequestHeader(Constants.USER_ID_HEADER) long userId,
                                             @PathVariable long bookingId){
        return bookingService.getBookingById(userId, bookingId);
    }
}
