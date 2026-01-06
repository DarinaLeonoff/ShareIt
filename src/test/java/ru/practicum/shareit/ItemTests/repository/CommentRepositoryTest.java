package ru.practicum.shareit.ItemTests.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.DbItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.DbUserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private DbItemRepository itemRepository;
    @Autowired
    private DbUserRepository userRepository;

    private User testUser;
    private Item testItem;
    private Comment testComment;

    @BeforeEach
    void setUp() {
        testUser = userRepository.save(Generators.generateUser(1L));
        testItem = itemRepository.save(Generators.generateItem(1L, testUser.getId()));
        testComment = commentRepository.save(Generators.generateComment(testUser, testItem));
    }

    @Test
    void testSaveComment() {
        List<Comment> comments = commentRepository.findAll();

        assertTrue(comments.size() == 1);
        assertEquals(comments.getFirst(), testComment);
    }

    @Test
    void testFindByExistItem() {
        List<Comment> comments = commentRepository.findByItemId(testItem.getId());

        assertTrue(comments.size() == 1);
        assertEquals(comments.getFirst(), testComment);
    }

    @Test
    void testFindByExistOwner() {
        List<Comment> comments = commentRepository.findAllByItemOwnerId(testUser.getId());

        assertTrue(comments.size() == 1);
        assertEquals(comments.getFirst(), testComment);
    }

    @Test
    void testFindByNotExistItem() {
        List<Comment> comments = commentRepository.findByItemId(200);

        assertTrue(comments.isEmpty());
    }

    @Test
    void testFindByNotExistOwner() {
        List<Comment> comments = commentRepository.findAllByItemOwnerId(200);

        assertTrue(comments.isEmpty());
    }

}
