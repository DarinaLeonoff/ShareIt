package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.Optional;

public interface UserRepository {
    User createUser(User user);

    User editUser(User user);

    Optional<User> findById(long id);

    void removeUser(long id);
}
