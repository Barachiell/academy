package com.ctw.workstation.rest.team.boundary;

import com.ctw.workstation.rest.team.control.TeamService;
import com.ctw.workstation.rest.team.entity.TeamDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/teams")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeamResource {

    @Inject
    private TeamService teamService;

    @GET
    public List<TeamDTO> getAllTeams(){
        return teamService.list();
    }

    @GET
    @Path("/{id}")
    public Response getTeam(@PathParam("id") UUID id) {
        TeamDTO teamDTO = teamService.findById(id);
        return Response.ok(teamDTO).build();
    }

    @POST
    public Response addTeam(@Valid @NotNull TeamDTO teamDTO){
        TeamDTO persistedtTeamDTO = teamService.save(teamDTO);
        return Response.status(Response.Status.CREATED).entity(persistedtTeamDTO).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTeam(@PathParam("id") UUID id, @Valid @NotNull TeamDTO teamDTO){
        TeamDTO updatedTeamDTO = teamService.
                update(new TeamDTO(id, teamDTO.product(), teamDTO.name(), teamDTO.defaultLocation()));
        return Response.ok(updatedTeamDTO).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTeam(@PathParam("id") UUID id){
        teamService.remove(id);
        return Response.noContent().build();
    }

}
