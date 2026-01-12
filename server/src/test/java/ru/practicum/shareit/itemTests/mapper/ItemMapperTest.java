//package ru.practicum.shareit.ItemTests.mapper;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.practicum.shareit.Generators;
//import ru.practicum.shareit.booking.dto.BookingDateDto;
//import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
//import ru.practicum.shareit.item.dto.item.ItemDto;
//import ru.practicum.shareit.item.dto.item.ItemWithCommentAndBookingDto;
//import ru.practicum.shareit.item.mapper.ItemMapper;
//import ru.practicum.shareit.item.model.Item;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class ItemMapperTest {
//
//    private final ItemMapper itemMapper;
//
//    @Autowired
//    public ItemMapperTest(ItemMapper itemMapper) {
//        this.itemMapper = itemMapper;
//    }
//
//
//    @Test
//    public void convertTest() {
//        Item item = Generators.generateItem(1L);
//        long owner = item.getOwnerId();
//
//        ItemDto dto = itemMapper.mapToDto(item);
//        Item item2 = itemMapper.mapToItem(dto);
//        item2.setOwnerId(owner);
//
//        Assertions.assertEquals(item, item2);
//    }
//
//    @Test
//    void testMapItemToItemWithBooking_success() {
//        // Подготовка тестовых данных
//        Item item = Item.builder().build();
//        item.setId(1L);
//        item.setName("Название");
//        item.setDescription("Описание");
//        item.setAvailable(true);
//
//        BookingDateDto lastBooking = BookingDateDto.builder().start(LocalDateTime.now()).end(LocalDateTime.now().plusDays(1)).build();
//        BookingDateDto nextBooking = BookingDateDto.builder().start(LocalDateTime.now().plusDays(2)).end(LocalDateTime.now().plusDays(3)).build();
//        CommentResponseDto comment = CommentResponseDto.builder().id(1L).text("Хороший товар").authorName("Автор").created(LocalDateTime.now()).build();
//
//        List<CommentResponseDto> comments = List.of(comment);
//
//        // Выполнение маппинга
//        ItemWithCommentAndBookingDto result = itemMapper.mapItemToItemWithBooking(item, lastBooking, nextBooking, comments);
//
//        // Проверки
//        assertThat(result.getId()).isEqualTo(item.getId());
//        assertThat(result.getName()).isEqualTo(item.getName());
//        assertThat(result.getDescription()).isEqualTo(item.getDescription());
//        assertThat(result.getAvailable()).isEqualTo(item.isAvailable());
//        assertThat(result.getLastBooking()).isEqualTo(lastBooking);
//        assertThat(result.getNextBooking()).isEqualTo(nextBooking);
//        assertThat(result.getComments()).isEqualTo(comments);
//    }
//}
