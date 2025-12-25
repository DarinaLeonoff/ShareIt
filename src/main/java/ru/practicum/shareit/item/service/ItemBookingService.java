package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDateDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exceptions.WrongRequestException;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.item.ItemWithCommentAndBookingDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.List;


//Чтобы избежать взаимозависимости ItemService и BookingService, общая логика для Item вынесена отдельно
@Slf4j
@Service
public class ItemBookingService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final BookingService bookingService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public ItemBookingService(@Qualifier("DbItemRepo") ItemRepository itemRepository, ItemMapper itemMapper, BookingService bookingService, CommentRepository commentRepository, CommentMapper commentMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.bookingService = bookingService;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public List<ItemWithCommentAndBookingDto> getAllUserItems(long userId) {
        return itemRepository.findByOwnerId(userId).stream().map(i -> {
            List<BookingDateDto> bookings = bookingService.getLastNextBookingByItemId(i.getId());
            List<CommentResponseDto> comments = commentRepository.findByItemId(i.getId()).stream().map(commentMapper::mapCommentToResponse).toList();
            return itemMapper.mapItemToItemWithBooking(i, bookings.get(0), bookings.get(1), comments);
        }).toList();
    }

    public ItemWithCommentAndBookingDto getItemWithCommentById(long itemId) {
        return itemRepository.findById(itemId).map(i -> {
            List<BookingDateDto> bookings = bookingService.getLastNextBookingByItemId(i.getId());
            List<CommentResponseDto> comments = commentRepository.findByItemId(i.getId()).stream().map(commentMapper::mapCommentToResponse).toList();
            return itemMapper.mapItemToItemWithBooking(i, bookings.get(0), bookings.get(1), comments);
        }).get();
    }

    public void checkUserHasBooking(long userId, long itemId) {
        List<Booking> bookings = bookingService.userItemBooking(userId, itemId);
        if (bookings.isEmpty()) {
            throw new WrongRequestException("Пользователь который еще не пользовался предметом, не может оставить на" + " него отзыв.");
        }
    }
}
