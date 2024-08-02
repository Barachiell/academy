package com.ctw.workstation.rest.booking.control;

import com.ctw.workstation.rest.booking.entity.Booking;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class BookingRepository implements PanacheRepositoryBase<Booking, UUID> {

}
