package ru.practicum.shareit.bookingTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.service.BookingState;
import ru.practicum.shareit.constants.Constants;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper mapper;

    private String url = "/bookings";
    private long userId = 1L;
    private long itemId = 1L;

    @Test
    void testMakeBooking() throws Exception {
        BookingRequestDto req = Generators.generateBookingRequest(itemId);
        BookingResponseDto res = Generators.generateBookingResponse();

        when(bookingService.makeBooking(req, userId)).thenReturn(res);

        mvc.perform(post(url)
                        .header(Constants.USER_ID_HEADER, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(res.getId()));
    }

    @Test
    void testApproveBooking() throws Exception {
        BookingResponseDto res = Generators.generateBookingResponse();
        res.setStatus(BookingStatus.APPROVED);

        when(bookingService.approveBooking(userId, res.getId(), true))
                .thenReturn(res);

        mvc.perform(patch(url + "/{bookingId}?approved=true", res.getId())
                        .header(Constants.USER_ID_HEADER, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(res.getId()))
                .andExpect(jsonPath("status").value(res.getStatus().name()));
    }

    @Test
    void testGetBookingById() throws Exception {
        BookingResponseDto res = Generators.generateBookingResponse();
        when(bookingService.getBookingById(userId, res.getId()))
                .thenReturn(res);

        mvc.perform(get(url + "/{bookingId}", res.getId())
                        .header(Constants.USER_ID_HEADER, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(res.getId()));
    }

    @Test
    void testGetAllUserBookings() throws Exception {
        List<BookingResponseDto> res = List.of(Generators.generateBookingResponse());
        when(bookingService.getAllUserBooking(userId, BookingState.ALL))
                .thenReturn(res);

        mvc.perform(get(url + "?state=ALL")
                        .header(Constants.USER_ID_HEADER, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]id").value(res.get(0).getId()));
    }

    @Test
    void testGetAllUserItemBooking() throws Exception {
        List<BookingResponseDto> res = List.of(Generators.generateBookingResponse());
        when(bookingService.getAllUserItemBooking(userId, BookingState.ALL))
                .thenReturn(res);

        mvc.perform(get(url + "/owner")
                        .header(Constants.USER_ID_HEADER, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]id").value(res.get(0).getId()));
    }

}
