package com.ctw.workstation.rest.teammember.boundary;

import com.ctw.workstation.rest.teammember.control.TeamMemberService;
import com.ctw.workstation.rest.teammember.entity.TeamMemberDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/teammembers")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeamMemberResource {

    @Inject
    private TeamMemberService teamMemberService;

    @GET
    public List<TeamMemberDTO> getAllTeamMembers(){
        return teamMemberService.list();
    }

    @GET
    @Path("/{id}")
    public Response getTeamMember(@PathParam("id") UUID id){
        TeamMemberDTO teamMemberDTO = teamMemberService.findById(id);
        return Response.ok(teamMemberDTO).build();
    }

    @POST
    public Response addTeamMember(@Valid TeamMemberDTO teamMemberDTO){
        TeamMemberDTO persistedTeamDTO = teamMemberService.save(teamMemberDTO);
        return Response.status(Response.Status.CREATED).entity(persistedTeamDTO).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTeamMember(@PathParam("id") UUID id, @Valid @NotNull TeamMemberDTO teamMemberDTO){
        TeamMemberDTO updatedTeamMemberDTO = teamMemberService.
                update(new TeamMemberDTO(id, teamMemberDTO.teamId(), teamMemberDTO.name(), teamMemberDTO.ctwId()));
        return Response.ok(updatedTeamMemberDTO).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTeamMember(@PathParam("id") UUID id){
        teamMemberService.remove(id);
        return Response.noContent().build();
    }
}
