package ru.practicum.shareit.user;

public interface UserService {
    User createUser(User user);
    User editUser(User user, long userId);
    User getUser(long userId);
    void removeUser(long userId);
}
