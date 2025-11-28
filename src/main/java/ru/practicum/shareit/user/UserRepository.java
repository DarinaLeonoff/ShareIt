package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Qualifier;

public interface UserRepository {
    User createUser(User user);
    User editUser(User user, long userId);
    User getUser(long id);
    void removeId(long id);
}
