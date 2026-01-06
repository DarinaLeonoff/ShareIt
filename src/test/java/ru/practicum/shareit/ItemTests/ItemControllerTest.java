package ru.practicum.shareit.ItemTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.constants.Constants;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.comment.NewCommentDto;
import ru.practicum.shareit.item.dto.item.ItemRequestDto;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.item.dto.item.ItemWithCommentAndBookingDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper mapper;

    private ItemRequestDto item = Generators.generateItemRequest();
    private long userId = 1L;
    private long itemId = 1L;
    private String url = "/items";

    @Test
    void testAddItem() throws Exception {
        ItemResponseDto resp = ItemResponseDto.builder()
                .id(1L)
                .name(item.getName())
                .description(item.getDescription())
                .available(Boolean.TRUE)
                .build();

        when(itemService.createItem(userId, item))
                .thenReturn(resp);

        mockMvc.perform(post(url)
                        .header(Constants.USER_ID_HEADER, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(resp.getId()))
                .andExpect(jsonPath("name").value(resp.getName()))
                .andExpect(jsonPath("description").value(resp.getDescription()))
                .andExpect(jsonPath("available").value(resp.getAvailable()));

    }

    @Test
    void testGetAllUserItems() throws Exception {
        List<ItemWithCommentAndBookingDto> items = List.of(Generators.generateItemWithCommentAndBooking(1L));

        when(itemService.getAllUserItems(userId))
                .thenReturn(items);

        mockMvc.perform(get(url)
                        .header(Constants.USER_ID_HEADER, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(items.getFirst().getName()))
                .andExpect(jsonPath("$[0].comments").isEmpty())
                .andExpect(jsonPath("$[0].lastBooking").doesNotExist())
                .andExpect(jsonPath("$[0].nextBooking").doesNotExist());
    }

    @Test
    void testGetItem() throws Exception {
        ItemWithCommentAndBookingDto item1 = Generators.generateItemWithCommentAndBooking(1L);
        when(itemService.getItemWithCommentById(userId, itemId)).thenReturn(item1);

        mockMvc.perform(get(url + "/{itemId}", itemId)
                        .header(Constants.USER_ID_HEADER, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(item1.getName()));
    }

    @Test
    void testEditItem() throws Exception {
        ItemResponseDto resp = ItemResponseDto.builder()
                .id(itemId)
                .name(item.getName())
                .description(item.getDescription())
                .available(true)
                .build();
        when(itemService.editItem(userId, item, itemId)).thenReturn(resp);

        mockMvc.perform(patch(url + "/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(resp.getName()));
    }

    @Test
    void testSearchItems() throws Exception {
        List<ItemResponseDto> searchResults = List.of(Generators.generateItemResponse(itemId));
        when(itemService.search(anyString())).thenReturn(searchResults);

        mockMvc.perform(get(url + "/search?text=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(searchResults.getFirst().getName()));
    }

    @Test
    void testAddComment() throws Exception {
        NewCommentDto newCommentDto = Generators.generateNewComment();
        CommentResponseDto comment = Generators.generateCommentResponse();

        when(itemService.addComment(userId, itemId, newCommentDto))
                .thenReturn(comment);

        mockMvc.perform(post(url + "/{itemId}/comment", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newCommentDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(".text").value(comment.getText()));
    }
}