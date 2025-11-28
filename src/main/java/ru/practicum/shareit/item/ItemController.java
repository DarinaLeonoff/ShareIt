package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @Valid @RequestBody ItemDto itemDto){
        log.info("Post new item. Item is {}, owner id is {}", itemDto.getName(), userId);
        return itemService.createItem(userId, itemDto);
    }

    @GetMapping
    public List<ItemDto> getAllUserItems(@RequestHeader("X-Sharer-User-Id") Long userId){
        log.info("Getting all items for user with id = {}", userId);
        return null;
    }

    @PatchMapping("/{itemId}")
    public ItemDto editItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                            @RequestBody ItemDto itemDto){
        log.info("Method not ready to work");
        log.info("Edit {}, owner id is {}", itemDto.getName(), userId);
        //return only if userId is owner
        return null;
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable Long itemId){
        log.info("Method not ready to work");
        log.info("Getting information about item with id = {}", itemId);
        return null;
    }

    @GetMapping("/items/search")
    public List<ItemDto> searchItems (@PathParam("text") String text){
        log.info("Method not ready to work");
        log.info("Getting items by request: {}", text);
        //only available items
        return null;
    }


}
