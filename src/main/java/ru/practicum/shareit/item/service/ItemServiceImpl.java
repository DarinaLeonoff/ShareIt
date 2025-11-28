package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    @Qualifier("inMemoryRepo")
    private final ItemRepository itemRepository;
    private final UserService userService;
    @Override
    public ItemDto createItem(long userId, ItemDto itemDto) {
        User user = userService.getUser(userId);
        return itemRepository.createItem(userId, itemDto);
    }

    @Override
    public List<ItemDto> getAllUserItems(long userId) {
        return itemRepository.getAllUserItems(userId);
    }

    @Override
    public ItemDto getItemById(long itemId){
        return itemRepository.getItemById(itemId);
    }

    @Override
    public ItemDto editItem(long userId, ItemDto itemDto, long itemId) {
        return itemRepository.editItem(userId, itemDto, itemId);
    }

    @Override
    public List<ItemDto> search(String text) {
        return itemRepository.search(text);
    }
}
