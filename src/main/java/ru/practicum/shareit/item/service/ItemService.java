package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.comment.NewCommentDto;
import ru.practicum.shareit.item.dto.item.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(long userId, ItemDto itemDto);

    ItemDto getItemDtoById(long itemId);

    ItemDto editItem(long userId, ItemDto itemDto, long itemId);

    List<ItemDto> search(String text);

    CommentResponseDto addComment(Long userId, long itemId, NewCommentDto dto);

}
