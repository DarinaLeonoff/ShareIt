package ru.practicum.shareit.user.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@Repository
@Qualifier("dbRepo")
public interface DbUserRepository extends JpaRepository<User, Long> {
    @Override
    User save(User user);

    Optional<User> findById(long id);

    default void removeUser(long id) {
        deleteById(id);
    }
}
