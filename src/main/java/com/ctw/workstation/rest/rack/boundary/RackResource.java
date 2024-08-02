package com.ctw.workstation.rest.rack.boundary;

import com.ctw.workstation.rest.rack.control.RackService;
import com.ctw.workstation.rest.rack.entity.Rack;
import com.ctw.workstation.rest.rack.entity.RackDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;


@Path("/racks")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RackResource {

    @Inject
    private RackService rackService;


    @GET
    @Path("/{id}")
    public Response getRack(@PathParam("id") UUID id){
        RackDTO rackDTO = rackService.findById(id);
        return Response.ok(rackDTO).build();
    }

    @POST
    public Response addRack(@Valid @NotNull RackDTO rackDTO){
        RackDTO persistedRackDTO = rackService.save(rackDTO);
        return Response.status(Response.Status.CREATED).entity(persistedRackDTO).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateRack(@PathParam("id") UUID id, @Valid @NotNull RackDTO rackDTO){
        RackDTO updatedRackDTO = rackService
                .update(new RackDTO(id, rackDTO.teamId(), rackDTO.serialNumber(), rackDTO.status(), rackDTO.defaultLocation()));
        return Response.ok(updatedRackDTO).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRack(@PathParam("id") UUID id){
        rackService.remove(id);
        return Response.noContent().build();
    }


    @GET
    public List<RackDTO> getRacks(@QueryParam("status") Rack.Status status){
        if(status == null){
            return rackService.list();
        }
        return rackService.listByStatus(status);
    }



}
