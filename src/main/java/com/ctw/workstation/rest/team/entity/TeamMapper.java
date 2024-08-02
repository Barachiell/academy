package com.ctw.workstation.rest.team.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel ="cdi")
public interface TeamMapper{

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "defaultLocation", source = "defaultLocation")
    TeamDTO mapToDTO(Team team);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "defaultLocation", source = "defaultLocation")
    Team mapToEntity(TeamDTO teamDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "defaultLocation", source = "defaultLocation")
    Team mapToEntity(TeamDTO teamDTO, @MappingTarget Team team);

    List<TeamDTO> teamsToTeamDTOs(List<Team> teams);

}
