package com.ctw.workstation.rest.booking.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel ="cdi")
public interface BookingMapper{

    @Mapping(target = "id", source = "id")
    @Mapping(target = "bookFrom", source = "bookFrom")
    @Mapping(target = "bookTo", source = "bookTo")
    @Mapping(target = "requesterId", source = "requesterId")
    @Mapping(target = "rackId", source = "rackId")
    BookingDTO mapToDTO(Booking booking);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "bookFrom", source = "bookFrom")
    @Mapping(target = "bookTo", source = "bookTo")
    @Mapping(target = "requesterId", source = "requesterId")
    @Mapping(target = "rackId", source = "rackId")
    Booking mapToEntity(BookingDTO bookingDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "bookFrom", source = "bookFrom")
    @Mapping(target = "bookTo", source = "bookTo")
    @Mapping(target = "requesterId", source = "requesterId")
    @Mapping(target = "rackId", source = "rackId")
    Booking mapToEntity(BookingDTO bookingDTO, @MappingTarget Booking booking);

    List<BookingDTO> bookingsToBookingDTOs(List<Booking> bookings);

}
