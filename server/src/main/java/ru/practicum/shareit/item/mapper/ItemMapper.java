package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.dto.BookingDateDto;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.item.AnswerDto;
import ru.practicum.shareit.item.dto.item.ItemRequestDto;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.item.dto.item.ItemWithCommentAndBookingDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    List<ItemResponseDto> mapItemsToResponses(List<Item> items);

    @Mapping(target = "ownerId", source = "userId")
    Item mapRequestToItem(ItemRequestDto dto, long userId);

    Item mapResponseToItem(ItemResponseDto dto);

    ItemResponseDto mapItemToResponse(Item item);

    @Mapping(target = "lastBooking", source = "lastBooking")
    @Mapping(target = "nextBooking", source = "nextBooking")
    @Mapping(target = "id", source = "item.id")
    @Mapping(target = "comments", source = "comments")
    ItemWithCommentAndBookingDto mapItemToItemWithBooking(Item item, BookingDateDto lastBooking, BookingDateDto nextBooking, List<CommentResponseDto> comments);

    AnswerDto mapItemToAnswer(Item item);

    default Item updateItem(Item item, ItemRequestDto dto) {
        if (dto.getName() != null && !dto.getName().isBlank()) {
            item.setName(dto.getName());
        }

        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            item.setDescription(dto.getDescription());
        }

        if (dto.getAvailable() != null) {
            item.setAvailable(dto.getAvailable());
        }
        return item;
    }
}
