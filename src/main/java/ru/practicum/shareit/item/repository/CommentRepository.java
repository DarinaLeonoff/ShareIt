package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Override
    Comment save(Comment comment);
}
