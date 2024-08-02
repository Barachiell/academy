package com.ctw.workstation.rest.teammember.entity;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel ="cdi")
public interface TeamMemberMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "teamId", source = "teamId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "ctwId", source = "ctwId")
    TeamMemberDTO mapToDTO(TeamMember teamMember);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "teamId", source = "teamId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "ctwId", source = "ctwId")
    TeamMember mapToEntity(TeamMemberDTO teamMemberDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "teamId", source = "teamId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "ctwId", source = "ctwId")
    TeamMember mapToEntity(TeamMemberDTO teamMemberDTO, @MappingTarget TeamMember teamMember);

    List<TeamMemberDTO> teamMembersToTeamMemberDTOs(List<TeamMember> teamMembers);

}
