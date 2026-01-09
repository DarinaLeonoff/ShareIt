package ru.practicum.shareit.requestTest;

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
import ru.practicum.shareit.request.dto.RequestItemDto;
import ru.practicum.shareit.request.dto.RequestItemResponseDto;
import ru.practicum.shareit.request.service.RequestService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemRequestControllerTest {
    @Autowired
    ObjectMapper mapper;

    @MockBean
    RequestService requestService;

    @Autowired
    private MockMvc mvc;

    private String url = "/requests";
    private long userId = 1L;

    @Test
    void makeRequestTest() throws Exception {
        RequestItemDto req = Generators.generateRequestItemDto();
        RequestItemResponseDto resp = Generators.genereteRequestResponse();
        when(requestService.makeRequest(userId, req)).thenReturn(resp);

        mvc.perform(post(url)
                        .header(Constants.USER_ID_HEADER, userId)
                        .content(mapper.writeValueAsString(req))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(resp.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(resp.getDescription())));
    }

    @Test
    void getUserRequestTest() throws Exception {
        RequestItemResponseDto resp = Generators.genereteRequestResponse();
        when(requestService.getUserRequest(userId)).thenReturn(List.of(resp));

        mvc.perform(get(url).header(Constants.USER_ID_HEADER, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(resp.getId()), Long.class))
                .andExpect(jsonPath("$[0].description", is(resp.getDescription())));
    }

    @Test
    void getAllRequestTest() throws Exception {
        RequestItemResponseDto resp = Generators.genereteRequestResponse();
        when(requestService.getAllRequest()).thenReturn(List.of(resp));

        mvc.perform(get(url + "/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(resp.getId()), Long.class))
                .andExpect(jsonPath("$[0].description", is(resp.getDescription())));
    }

    @Test
    void getRequestTest() throws Exception {
        RequestItemResponseDto resp = Generators.genereteRequestResponse();
        when(requestService.getRequest(resp.getId())).thenReturn(resp);

        mvc.perform(get(url + "/{requestId}", resp.getId())
                        .header(Constants.USER_ID_HEADER, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(resp.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(resp.getDescription())));
    }
}
