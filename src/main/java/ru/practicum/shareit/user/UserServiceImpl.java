package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Qualifier("inMemoryRepo")
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public User editUser(User user, long userId) {
        return null;
    }

    @Override
    public User getUser(long userId) {
        return null;
    }

    @Override
    public void removeUser(long userId) {

    }


}
