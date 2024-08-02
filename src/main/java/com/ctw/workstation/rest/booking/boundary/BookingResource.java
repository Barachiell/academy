package com.ctw.workstation.rest.booking.boundary;

import com.ctw.workstation.rest.booking.control.BookingService;
import com.ctw.workstation.rest.booking.entity.BookingDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.UUID;


@Path("/bookings")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookingResource {

    //Logger logger = Logger.getLogger(BookingResource.class);

    @Inject
    private BookingService bookingService;



    @GET
    public List<BookingDTO> getAllBookings(){
        //logger.info("Fetching All Bookings");
        return bookingService.list();
    }

    @GET
    @Path("/{id}")
    public Response getBooking(@PathParam("id") UUID id){
        //logger.info("Performing GET request for booking id: " + id);
        BookingDTO bookingDTO = bookingService.findById(id);
        return Response.ok(bookingDTO).build();
    }

    @POST
    public Response addBooking(@Valid @NotNull BookingDTO bookingDTO){
        BookingDTO persistedBookingDTO = bookingService.save(bookingDTO);
        return Response.status(Response.Status.CREATED).entity(persistedBookingDTO).build();
    }


    @PUT
    @Path("/{id}")
    public Response updateBooking(@PathParam("id") UUID id, @Valid @NotNull BookingDTO bookingDTO){
        BookingDTO updatedBookingDTO =  bookingService
                .update(new BookingDTO(id, bookingDTO.bookFrom(), bookingDTO.bookTo(), bookingDTO.requesterId(), bookingDTO.rackId()));
        return Response.ok(updatedBookingDTO).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBooking(@PathParam("id") UUID id){
        bookingService.remove(id);
        return Response.noContent().build();
    }

}
