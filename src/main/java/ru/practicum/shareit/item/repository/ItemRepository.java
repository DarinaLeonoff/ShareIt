package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemRepository {
    ItemDto createItem(long userId, ItemDto itemDto);
    List<ItemDto> getAllUserItems(long userId);
    ItemDto editItem(long userId, ItemDto itemDto);
}
