package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @EntityGraph(attributePaths = {"status"})
    Booking save(Booking booking);

    Booking findById(long id);

    List<Booking> findByBookerId(long userId);

    @Query("SELECT b FROM Booking b JOIN b.item i WHERE i.ownerId = :userId")
    List<Booking> findAllByOwnerId(@Param("userId") long userId);

    @Query("SELECT b FROM Booking b WHERE b.end < :currentDate AND b.item.id= :itemId AND b.status = 2 ORDER BY b.end DESC LIMIT 1")
    Optional<Booking> findLastBooking(@Param("currentDate") LocalDateTime currentDate, @Param("itemId") long itemId);

    @Query("SELECT b FROM Booking b WHERE b.start > :currentDate AND b.item.id = :itemId AND b.status = 2 ORDER BY b.start ASC LIMIT 1")
    Optional<Booking> findNextBooking(@Param("currentDate") LocalDateTime currentDate, @Param("itemId") long itemId);
}
