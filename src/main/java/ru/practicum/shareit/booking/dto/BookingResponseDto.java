package ru.practicum.shareit.booking.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
@Data
public class BookingResponseDto {
        private long id;
        private ItemDto item;
        private User booker;
        private LocalDateTime start;
        private LocalDateTime end;
        private BookingStatus status;
}
