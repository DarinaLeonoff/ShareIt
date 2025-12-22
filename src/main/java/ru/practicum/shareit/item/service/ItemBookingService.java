package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDateDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemWithBookingDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.List;

@Slf4j
@Service
public class ItemBookingService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final BookingService bookingService;

    public ItemBookingService(@Qualifier("DbItemRepo") ItemRepository itemRepository, ItemMapper itemMapper,
            BookingService bookingService) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.bookingService = bookingService;
    }

    public List<ItemWithBookingDto> getAllUserItems(long userId) {
        return itemRepository.findByOwnerId(userId).stream().map(i -> {
            List<BookingDateDto> bookings = bookingService.getLastNextBookingByItemId(i.getId());
            return itemMapper.mapItemToItemWithBooking(i, bookings.get(0), bookings.get(1));
        }).toList();
    }
}
