package ru.practicum.shareit.userTests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//    private UserDto user;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private long i = 3;
//
//    @BeforeEach
//    void setup() {
//        user = Generators.generateUser(++i);
//    }
//
//    @Test
//    void testAddUser() throws Exception {
//
//        when(userService.createUser(user)).thenReturn(user);
//
//        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user))).andExpect(status().isCreated()).andExpect(jsonPath("id").value(user.getId())).andExpect(jsonPath("name").value(user.getName())).andExpect(jsonPath("email").value(user.getEmail()));
//    }
//
//    @Test
//    void testGetUserById() throws Exception {
//        when(userService.createUser(user)).thenReturn(user);
//
//        when(userService.getUser(user.getId())).thenReturn(user);
//
//        mockMvc.perform(get("/users/{userId}", user.getId())).andExpect(status().isOk()).andExpect(jsonPath("id").value(user.getId())).andExpect(jsonPath("name").value(user.getName())).andExpect(jsonPath("email").value(user.getEmail()));
//    }
//
//    @Test
//    void testEditUser() throws Exception {
//        // Создаем исходный пользователь
//        UserDto originalUser = Generators.generateUser(1L);
//
//        // Мокаем создание пользователя
//        when(userService.createUser(originalUser)).thenReturn(originalUser);
//
//        // Создаем обновленные данные
//        UserDto updatedUser = new UserDto();
//        updatedUser.setId(originalUser.getId());
//        updatedUser.setName("Updated Name");
//        updatedUser.setEmail("new-email@example.com");
//        // Мокаем метод обновления
//        when(userService.editUser(updatedUser, originalUser.getId())).thenReturn(updatedUser);
//
//        mockMvc.perform(patch("/users/{userId}", originalUser.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedUser))).andExpect(status().isOk()).andExpect(jsonPath("id").value(updatedUser.getId())).andExpect(jsonPath("name").value(updatedUser.getName())).andExpect(jsonPath("email").value(updatedUser.getEmail()));
//    }
//
//    @Test
//    void testNotFoundUser() throws Exception {
//        Long nonExistingId = 999L;
//
//        // Важно вернуть null, чтобы симулировать ситуацию, когда пользователь не найден
//        when(userService.getUser(nonExistingId))
//                .thenThrow(new NotFoundException());
//
//        mockMvc.perform(get("/users/{userId}", nonExistingId))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testInvalidUser() throws Exception {
//        User invalidUser = new User();
//        invalidUser.setName("");
//        invalidUser.setEmail("invalid-email");
//
//        mockMvc.perform(post("/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(invalidUser)))
//                .andExpect(status().isBadRequest());
//    }
}
