package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.ValidationException;

import java.util.List;
import java.util.Set;

@Slf4j
@Repository
@Qualifier("inMemoryRepo")
@RequiredArgsConstructor
public class InMemoryUserRepository implements UserRepository{
    private final Set<User> usersStorage;

    private long maxId = 0;

    @Override
    public User createUser(User user) {
        List<User> users = usersStorage.stream().filter(u -> u.getEmail().equals(user.getEmail())).toList();
        if(users.size() > 0){
            log.warn("Try to add new user with registered email");
            throw new ValidationException("Email was already registered.");
        }
        user.setId(generateId());
        usersStorage.add(user);
        if(usersStorage.contains(user)){
            log.info("User was successful added to memory");
        }
        return user;
    }

    @Override
    public User editUser(User user, long userId) {
        return null;
    }

    @Override
    public User getUser(long id) {
        return null;
    }

    @Override
    public void removeId(long id) {

    }

    private long generateId(){
        return ++maxId;
    }
}
