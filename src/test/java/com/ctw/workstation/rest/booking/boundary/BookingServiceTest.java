package com.ctw.workstation.rest.booking.boundary;

import com.ctw.workstation.rest.booking.control.BookingRepository;
import com.ctw.workstation.rest.booking.control.BookingService;
import com.ctw.workstation.rest.booking.entity.Booking;
import com.ctw.workstation.rest.booking.entity.BookingDTO;
import com.ctw.workstation.rest.booking.entity.BookingMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class BookingServiceTest {

    @Inject
    BookingService bookingService;

    @InjectMock
    BookingRepository mockBookingRepository;

    @Inject
    BookingMapper bookingMapper;


    @Test
    void findByIdWhereBookingNull() {
        // given or arrange
        UUID id = UUID.randomUUID();
        // when
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            //Code under test
            bookingService.findById(id);
        });

        // then
        //assertEquals("Booking With Id: " + id + " Not Found", thrown.getMessage());
    }

    @Test
    void findByIdWhitValidBooking() {

        // given or arrange
        Booking booking = new Booking();
        BookingDTO bookingExpected = bookingMapper.mapToDTO(booking);

        UUID id = UUID.randomUUID();

        Mockito.when(mockBookingRepository.findById(id)).thenReturn(booking);

        // when
        BookingDTO bookingResult = bookingService.findById(id);

        assertEquals(bookingExpected, bookingResult);

        // then

    }
}
    /*
    @BeforeEach
    void setUp() {
        mockStatic(Booking.class);
    }

    @Test
    void testFindBookingById_BookingExists() {
        Long bookingId = 1L;
        Booking booking = new Booking();
        BookingDTO bookingDTO = new BookingDTO();

        when(Booking.findById(bookingId)).thenReturn(booking);
        when(bookingMapper.mapToDTO(booking)).thenReturn(bookingDTO);

        BookingDTO result = bookingService.findBookingById(bookingId);

        assertNotNull(result);
        assertEquals(bookingDTO, result);
        verifyStatic(Booking.class);
        Booking.findById(bookingId);
        verify(bookingMapper, times(1)).mapToDTO(booking);
    }

    @Test
    void testFindBookingById_BookingNotExists() {
        Long bookingId = 1L;

        when(Booking.findById(bookingId)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            bookingService.findBookingById(bookingId);
        });

        assertEquals("Booking With Id: " + bookingId + " Not Found", exception.getMessage());
        verifyStatic(Booking.class);
        Booking.findById(bookingId);
    }
}

    @Test
    void list() {
    }

    @Test
    void update() {
    }

    @Test
    void save() {
    }

    @Test
    void remove() {
    }
}


     */