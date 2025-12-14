package ru.practicum.shareit.user.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@Repository
@Qualifier("dbRepo")
public interface DbUserRepository extends JpaRepository<User, Long>, UserRepository{
    @Override
    default User createUser(User user){
        return save(user);
    }

    @Override
    default User editUser(User user){
        return save(user);
    }

    @Override
    Optional<User> findById(long id);

    @Override
    default void removeUser(long id){
        deleteById(id);
    }
}
