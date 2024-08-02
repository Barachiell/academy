package com.ctw.workstation.rest.booking.control;

import com.ctw.workstation.infrastructure.exceptions.NotFoundException;
import com.ctw.workstation.rest.booking.entity.Booking;
import com.ctw.workstation.rest.booking.entity.BookingDTO;
import com.ctw.workstation.rest.booking.entity.BookingMapper;
import com.ctw.workstation.rest.rack.control.RackService;
import com.ctw.workstation.rest.rack.entity.Rack;
import com.ctw.workstation.rest.teammember.control.TeamMemberService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@RequestScoped
public class BookingService {

    @Inject
    private BookingMapper bookingMapper;

    @Inject
    private RackService rackService;

    @Inject
    private TeamMemberService teamMemberService;

    @Inject
    private BookingRepository bookingRepository;

    public BookingDTO findById(UUID id){
        return bookingMapper.mapToDTO(bookingRepository.findByIdOptional(id)
                .orElseThrow(()-> new NotFoundException("Booking With Id: " + id + " Not Found")));
    }

    public List<BookingDTO> list() {
        return bookingMapper.bookingsToBookingDTOs(bookingRepository.listAll());
    }

    @Transactional
    public BookingDTO update(BookingDTO bookingDTO){
        Booking booking = bookingRepository.findByIdOptional(bookingDTO.id())
                .orElseThrow(()-> new NotFoundException("Booking With Id: " + bookingDTO.id() + " Not Found"));
        rackService.isRackAvailable(rackService.findById(bookingDTO.rackId()));
        teamMemberService.findById(bookingDTO.requesterId());
        bookingRepository.persist(bookingMapper.mapToEntity(bookingDTO, booking));
        return bookingMapper.mapToDTO(booking);
    }

    @Transactional
    public BookingDTO save(BookingDTO bookingDTO){
        rackService.isRackAvailable(rackService.findById(bookingDTO.rackId()));
        teamMemberService.findById(bookingDTO.requesterId());
        Booking booking = bookingMapper.mapToEntity(bookingDTO);
        bookingRepository.persist(booking);
        rackService.updateRackStatus(bookingDTO.rackId(), Rack.Status.UNAVAILABLE);
        return bookingMapper.mapToDTO(booking);
    }

    @Transactional
    public void remove(UUID id) {
        Booking booking = bookingRepository.findByIdOptional(id)
                .orElseThrow(()-> new NotFoundException("Booking With Id: " + id + " Not Found"));
        bookingRepository.delete(booking);
    }

}
