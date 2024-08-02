package com.ctw.workstation.rest.teammember.control;

import com.ctw.workstation.infrastructure.exceptions.NotFoundException;
import com.ctw.workstation.rest.team.control.TeamService;
import com.ctw.workstation.rest.teammember.entity.TeamMember;
import com.ctw.workstation.rest.teammember.entity.TeamMemberDTO;
import com.ctw.workstation.rest.teammember.entity.TeamMemberMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Dependent
public class TeamMemberService {

    @Inject
    private TeamMemberMapper teamMemberMapper;

    @Inject
    private TeamMemberRepository teamMemberRepository;

    @Inject
    private TeamService teamService;

    public TeamMemberDTO findById(UUID id) {
        return teamMemberMapper.mapToDTO(teamMemberRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("TeamMember with Id "+ id + " not found")));
    }

    public List<TeamMemberDTO> list() {
        return teamMemberMapper.teamMembersToTeamMemberDTOs(teamMemberRepository.listAll());
    }

    @Transactional
    public TeamMemberDTO save(TeamMemberDTO teamMemberDTO){
        //throw Team not found if Team doesn't exist
        teamService.findById(teamMemberDTO.teamId());
        TeamMember teamMember = teamMemberMapper.mapToEntity(teamMemberDTO);
        teamMemberRepository.persist(teamMember);
        return teamMemberMapper.mapToDTO(teamMember);
    }

    @Transactional
    public TeamMemberDTO update(TeamMemberDTO teamMemberDTO) {
        TeamMember teamMember = teamMemberRepository.findByIdOptional(teamMemberDTO.id())
                .orElseThrow(()-> new NotFoundException("TeamMember with Id "+ teamMemberDTO.id() + " not found"));
        //throw Team not found if Team doesn't exist
        teamService.findById(teamMemberDTO.teamId());
        teamMemberRepository.persist(teamMemberMapper.mapToEntity(teamMemberDTO, teamMember));
        return teamMemberMapper.mapToDTO(teamMember);
    }

    @Transactional
    public void remove(UUID id) {
        TeamMember teamMember = teamMemberRepository.findByIdOptional(id)
                .orElseThrow(()-> new NotFoundException("TeamMember with Id " + id + " not found"));
        teamMemberRepository.delete(teamMember);
    }

    @Transactional
    public void clearDatabase() {
        teamMemberRepository.deleteAll();
    }
}
