package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.comment.NewCommentDto;
import ru.practicum.shareit.item.dto.item.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.mapper.ItemMapperUtils;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public ItemServiceImpl(@Qualifier("DbItemRepo") ItemRepository itemRepository, UserService userService, ItemMapper itemMapper, UserMapper userMapper, CommentMapper commentMapper, CommentRepository commentRepository) {
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.itemMapper = itemMapper;
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public ItemDto createItem(long userId, ItemDto itemDto) {
        userService.getUser(userId);
        Item item = itemMapper.mapToItem(itemDto);
        item.setOwnerId(userId);
        return itemMapper.mapToDto(itemRepository.saveItem(item));
    }

    @Override
    public ItemDto getItemDtoById(long itemId) {
        return itemMapper.mapToDto(itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Предмет с таким id(" + itemId + ") не найден")));
    }

    @Override
    @Transactional
    public ItemDto editItem(long userId, ItemDto itemDto, long itemId) {
        Item oldItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Предмет с таким id(" + itemId + ") не найден"));
        if (oldItem.getOwnerId() != userId) {
            log.info("Попытка редактировать карточку предмета другого пользователя");
            throw new NotFoundException("Пользователь не является собственником данной вещи");
        }
        Item newItem = ItemMapperUtils.updateItem(oldItem, itemDto);
        return itemMapper.mapToDto(itemRepository.editItem(newItem));
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.searchByText(text).stream().map(itemMapper::mapToDto).toList();
    }

    @Override
    public CommentResponseDto addComment(Long userId, long itemId, NewCommentDto dto) {
        Comment com = commentMapper.mapNewCommentToComment(dto, userMapper.mapDtoToUser(userService.getUser(userId)), itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Пользователь не найден.")), LocalDateTime.now());
        return commentMapper.mapCommentToResponse(commentRepository.save(com));
    }

}
