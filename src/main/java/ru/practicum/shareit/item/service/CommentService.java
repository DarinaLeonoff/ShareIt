package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.NewCommentDto;
import ru.practicum.shareit.item.model.Comment;

public interface CommentService {
    CommentResponseDto addComment(Long userId, long itemId, NewCommentDto dto);
}
