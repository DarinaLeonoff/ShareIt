package ru.practicum.shareit.item.dto.item;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDateDto;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;

import java.util.List;

@Data
@Builder
@Valid
public class ItemWithCommentAndBookingDto {
    private long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;

    @Nullable
    private BookingDateDto lastBooking;
    @Nullable
    private BookingDateDto nextBooking;

    List<CommentResponseDto> comments;

}
