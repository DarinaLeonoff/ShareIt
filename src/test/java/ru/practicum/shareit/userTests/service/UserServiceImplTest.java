package ru.practicum.shareit.userTests.service;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class UserServiceImplTest {

//    @Autowired
//    private final UserService userService;
//
//    public UserServiceImplTest(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Test
//    void createAndGetUserTest() {
//        UserDto user = userService.createUser(Generators.generateUser(1L));
//        UserDto getUser = userService.getUser(user.getId());
//
//        assertEquals(user, getUser);
//    }
//
//    @Test
//    void editUserTest() {
//        UserDto user = userService.createUser(Generators.generateUser(1L));
//        UserDto userForUpdate = Generators.generateUser(user.getId());
//
//        UserDto updated = userService.editUser(userForUpdate, user.getId());
//        assertEquals(userForUpdate, updated);
//    }
//
//    @Test
//    void removeUserTest() {
//        UserDto user = userService.createUser(Generators.generateUser(1L));
//        userService.removeUser(user.getId());
//
//        assertThrows(NotFoundException.class, () -> {
//            userService.getUser(user.getId());
//        });
//    }
}
