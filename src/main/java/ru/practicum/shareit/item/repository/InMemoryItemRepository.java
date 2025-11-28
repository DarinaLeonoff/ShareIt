package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Slf4j
@Qualifier("inMemoryRepo")
@Repository
public class InMemoryItemRepository implements ItemRepository {
    private final Map<Long, Item> itemsRepository = new HashMap<>();
    private long maxId = 0;

    @Override
    public ItemDto createItem(long userId, ItemDto itemDto) {
        Item item = ItemMapper.mapToItem(itemDto, userId);
        item.setId(generateId());
        itemsRepository.put(item.getId(), item);
        return ItemMapper.mapToDto(item);
    }

    @Override
    public List<ItemDto> getAllUserItems(long userId) {
        return itemsRepository.values().stream().filter(i -> i.getOwnerId() == userId).map(ItemMapper::mapToDto).toList();
    }

    @Override
    public ItemDto getItemById(long itemId) {
        return ItemMapper.mapToDto(itemsRepository.get(itemId));
    }

    @Override
    public ItemDto editItem(long userId, ItemDto itemDto, long itemId) {
        Item item = itemsRepository.get(itemId);
        if(item.getOwnerId() != userId){
            log.warn("User {} is not owner", userId);
            throw new NotFoundException("User is not owner");
        }
        Item newItem = ItemMapper.updateItem(item, itemDto);
        itemsRepository.put(itemId, newItem);
        return ItemMapper.mapToDto(newItem);
    }

    @Override
    public List<ItemDto> search(String text) {
        if(text == null || text.isEmpty()){
            return new ArrayList<>();
        }
        String lowText = text.toLowerCase();
        return itemsRepository.values().stream()
                .filter(i ->
                        (i.getName().toLowerCase().contains(lowText) || i.getDescription().toLowerCase().contains(lowText))
                                && i.isAvailable())
                .map(ItemMapper::mapToDto).toList();
    }

    private long generateId(){
        return ++maxId;
   }
}
