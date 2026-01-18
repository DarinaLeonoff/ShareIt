package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.dto.BookingDateDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "item.id", source = "itemId")
    Booking mapRequestDtoToBooking(BookingRequestDto dto);

    @Mapping(target = "item", source = "booking.item")
    @Mapping(target = "booker", source = "booking.booker")
    @Mapping(target = "status", source = "status")
    BookingResponseDto mapBookingToResponseDto(Booking booking);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "start", source = "start")
    BookingDateDto mapBookingToDateDto(Booking booking);

//    @Qualifier
//    @Target(ElementType.METHOD)
//    @Retention(RetentionPolicy.CLASS)
//    public @interface ItemMapper {}
}
