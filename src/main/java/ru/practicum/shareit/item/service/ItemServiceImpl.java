package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.mapper.ItemMapperUtils;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final ItemMapper itemMapper;

    public ItemServiceImpl(@Qualifier("inMemoryRepo") ItemRepository itemRepository, UserService userService, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemDto createItem(long userId, ItemDto itemDto) {
        User user = userService.getUser(userId);
        Item item = itemRepository.createItem(userId, itemMapper.mapToItem(itemDto, userId));
        return itemMapper.mapToDto(item);
    }

    @Override
    public List<ItemDto> getAllUserItems(long userId) {
        return itemRepository.getAllUserItems(userId).stream().map(itemMapper::mapToDto).toList();
    }

    @Override
    public ItemDto getItemById(long itemId) {
        return itemMapper.mapToDto(itemRepository.getItemById(itemId));
    }

    @Override
    public ItemDto editItem(long userId, ItemDto itemDto, long itemId) {
        Item oldItem = itemRepository.getItemById(itemId);
        if (oldItem.getOwnerId() != userId) {
            log.info("Попытка редактировать карточку предмета другого пользователя");
            throw new NotFoundException("Пользователь не является собственником данной вещи");
        }
        Item newItem = ItemMapperUtils.updateItem(itemRepository.getItemById(itemId), itemDto);
        return itemMapper.mapToDto(itemRepository.editItem(newItem, itemId));
    }

    @Override
    public List<ItemDto> search(String text) {
        return itemRepository.search(text).stream().map(itemMapper::mapToDto).toList();
    }
}
