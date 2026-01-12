package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.Constants;
import ru.practicum.shareit.item.dto.ItemRequest;
import ru.practicum.shareit.item.dto.ItemUpdateRequest;
import ru.practicum.shareit.item.dto.NewComment;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader(Constants.USER_ID_HEADER) long userId,
                                             @Valid @RequestBody ItemRequest item) {
        log.info("Creating new item {}", item.getName());
        return itemClient.createItem(userId, item);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUserItems(@RequestHeader(Constants.USER_ID_HEADER) long userId) {
        log.info("Getting items for user id = {}", userId);
        return itemClient.getAllUserItems(userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@RequestHeader(Constants.USER_ID_HEADER) long userId,
                                          @PathVariable long itemId) {
        log.info("Getting item with id = {}", itemId);
        return itemClient.getItem(userId, itemId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> editItem(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                           @RequestBody ItemUpdateRequest item,
                                           @PathVariable long itemId) {
        log.info("Edit item with id = {}", itemId);
        return itemClient.editItem(userId, item, itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                         @RequestParam("text") String text) {
        log.info("Search item by string '{}'", text);
        return itemClient.searchItems(userId, text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(Constants.USER_ID_HEADER) Long userId,
                                             @PathVariable long itemId,
                                             @Valid @RequestBody NewComment comment) {
        log.info("Commenting item id={}", itemId);
        return itemClient.addComment(userId, itemId, comment);
    }

}
