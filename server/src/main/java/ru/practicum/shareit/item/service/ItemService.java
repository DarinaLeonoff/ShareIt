package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.comment.NewCommentDto;
import ru.practicum.shareit.item.dto.item.AnswerDto;
import ru.practicum.shareit.item.dto.item.ItemRequestDto;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.item.dto.item.ItemWithCommentAndBookingDto;

import java.util.List;
import java.util.Map;

public interface ItemService {
    ItemResponseDto createItem(long userId, ItemRequestDto itemDto);

    ItemResponseDto getItemDtoById(long itemId);

    ItemResponseDto editItem(long userId, ItemRequestDto itemDto, long itemId);

    List<ItemResponseDto> search(String text);

    CommentResponseDto addComment(long userId, long itemId, NewCommentDto dto);

    List<ItemWithCommentAndBookingDto> getAllUserItems(long userId);

    ItemWithCommentAndBookingDto getItemWithCommentById(long useId, long itemId);

    boolean isUserOwner(long itemId, long userId);

    Map<Long, List<AnswerDto>> getItemAnswerForRequests(List<Long> requestIds);
}
