package com.ctw.workstation.rest.rackasset.boundary;

import com.ctw.workstation.rest.rackasset.control.RackAssetService;
import com.ctw.workstation.rest.rackasset.entity.RackAssetDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/rackassets")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RackAssetResource {

    @Inject
    private RackAssetService rackAssetService;

    @GET
    public List<RackAssetDTO> getAllRackAssets() {
        return rackAssetService.list();
    }

    @GET
    @Path("/{id}")
    public Response getRackAsset(@PathParam("id") UUID id){
        RackAssetDTO rackAssetDTO = rackAssetService.findById(id);
        return Response.ok(rackAssetDTO).build();
    }

    @POST
    public Response addRackAsset(@Valid @NotNull RackAssetDTO rackAssetDTO){
        RackAssetDTO persistedRackAssetDTO = rackAssetService.save(rackAssetDTO);
        return Response.status(Response.Status.CREATED).entity(persistedRackAssetDTO).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateRackAsset(@PathParam("id") UUID id, @Valid @NotNull RackAssetDTO rackAssetDTO){
        RackAssetDTO rackAssetDTOWithId = new RackAssetDTO(id, rackAssetDTO.rackId(), rackAssetDTO.assetTag());
        RackAssetDTO updatedRackAssetDTO = rackAssetService.update(rackAssetDTOWithId);
        return Response.ok(updatedRackAssetDTO).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteRackAsset(@PathParam("id") UUID id){
        rackAssetService.remove(id);
        return Response.noContent().build();
    }


}
