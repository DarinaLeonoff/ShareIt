package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

@Repository
public interface DbItemRepository extends JpaRepository<Item, Long> {

    Item save(Item item);

    List<Item> findByOwnerId(long userId);

    @Override
    Optional<Item> findById(Long id);

    @Query("""
            SELECT i
            FROM Item i
            WHERE
                (LOWER(i.name) LIKE CONCAT('%', LOWER(:text), '%')
                OR LOWER(i.description) LIKE CONCAT('%', LOWER(:text), '%'))
                AND i.available = true
            """)
    List<Item> searchByText(@Param("text") String text);

    List<Item> findAllByRequestIdIn(List<Long> requestIds);
}
