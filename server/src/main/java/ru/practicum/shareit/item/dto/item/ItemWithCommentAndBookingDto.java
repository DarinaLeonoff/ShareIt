package ru.practicum.shareit.item.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDateDto;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemWithCommentAndBookingDto {
    private long id;
    private String name;
    private String description;
    private Boolean available;

    private BookingDateDto lastBooking;
    private BookingDateDto nextBooking;
    List<CommentResponseDto> comments;

}
