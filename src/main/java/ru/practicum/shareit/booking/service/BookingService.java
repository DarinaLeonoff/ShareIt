package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

public interface BookingService {
    BookingResponseDto makeBooking(BookingRequestDto dto, long userId);

    BookingResponseDto approveBooking(long userId, long bookingId, boolean approved);
}
