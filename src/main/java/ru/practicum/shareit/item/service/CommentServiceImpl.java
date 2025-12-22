package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.NewCommentDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public CommentResponseDto addComment(Long userId, long itemId, NewCommentDto dto) {
        log.info("Comment to add: {}", dto.getText());
        Comment com = commentMapper.mapNewCommentToComment(dto,
                userMapper.mapDtoToUser(userService.getUser(userId)),
                itemMapper.mapToItem(itemService.getItemById(itemId)), LocalDateTime.now());
        log.info("Comment to add: {}", com);
       return commentMapper.mapCommentToResponse(commentRepository.save(com));
    }
}
