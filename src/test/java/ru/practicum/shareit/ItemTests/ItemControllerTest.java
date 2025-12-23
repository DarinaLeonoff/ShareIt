package ru.practicum.shareit.ItemTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.item.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

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

    private ItemDto itemDto;
    private Long userId;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // Инициализация тестовых данных
        itemDto = new ItemDto();
        itemDto.setName("Test Item");
        itemDto.setDescription("Description");
        itemDto.setAvailable(true);

        userId = 1L;
    }

    @Test
    void testAddItem() throws Exception {
        // Мокаем ответ сервиса
        when(itemService.createItem(userId, itemDto)).thenReturn(itemDto);
        mockMvc.perform(post("/items").header("X-Sharer-User-Id", userId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(itemDto))).andExpect(status().isCreated()).andExpect(jsonPath("name").value("Test Item"));

    }

//    @Test
//    void testGetAllUserItems() throws Exception {
//        // Мокаем список элементов
//        List<ItemDto> items = List.of(itemDto);
//        when(itemService.getAllUserItems(userId)).thenReturn(items);
//
//        mockMvc.perform(get("/items").header("X-Sharer-User-Id", userId)).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Test Item"));
//    }

//    @Test
//    void testGetItemById() throws Exception {
//        Long itemId = 1L;
//        when(itemService.getItemWithCommentById(itemId)).thenReturn(itemDto);
//
//        mockMvc.perform(get("/items/{itemId}", itemId)).andExpect(status().isOk()).andExpect(jsonPath("name").value("Test Item"));
//    }

    @Test
    void testEditItem() throws Exception {
        Long itemId = 1L;
        when(itemService.editItem(userId, itemDto, itemId)).thenReturn(itemDto);

        mockMvc.perform(patch("/items/{itemId}", itemId).header("X-Sharer-User-Id", userId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(itemDto))).andExpect(status().isOk()).andExpect(jsonPath("name").value("Test Item"));
    }

    @Test
    void testSearchItems() throws Exception {
        List<ItemDto> searchResults = List.of(itemDto);
        when(itemService.search("test")).thenReturn(searchResults);

        mockMvc.perform(get("/items/search?text=test")).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Test Item"));
    }
}