package ru.practicum.shareit.userTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mvc;


    private UserRequestDto user;
    private UserResponseDto resp;

    @BeforeEach
    void setUp() {
        user = Generators.generateUserRequestDto();
        resp = UserResponseDto.builder()
                .id(1L)
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Test
    void testAddUser() throws Exception {
        when(userService.createUser(user))
                .thenReturn(resp);

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(user))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(resp.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(resp.getName())))
                .andExpect(jsonPath("$.email", is(resp.getEmail())));
    }

    @Test
    void testEditUser() throws Exception {
        when(userService.editUser(user, resp.getId()))
                .thenReturn(resp);

        mvc.perform(patch("/users/" + resp.getId())
                        .content(mapper.writeValueAsString(user))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(resp.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(resp.getName())))
                .andExpect(jsonPath("$.email", is(resp.getEmail())));

    }

    @Test
    void testGetUser() throws Exception {
        when(userService.getUser(resp.getId()))
                .thenReturn(resp);

        mvc.perform(get("/users/" + resp.getId())
                        .content(mapper.writeValueAsString(user))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(resp.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(resp.getName())))
                .andExpect(jsonPath("$.email", is(resp.getEmail())));
    }

    @Test
    void testRemoveUser() throws Exception {
        mvc.perform(delete("/users/" + resp.getId()))
                .andExpect(status().isNoContent());

        Mockito.verify(userService, times(1)).removeUser(resp.getId());
    }
}
