package com.ctw.workstation.rest.team.control;

import com.ctw.workstation.infrastructure.exceptions.NotFoundException;
import com.ctw.workstation.rest.team.entity.Team;
import com.ctw.workstation.rest.team.entity.TeamDTO;
import com.ctw.workstation.rest.team.entity.TeamMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@RequestScoped
public class TeamService {

    @Inject
    private TeamMapper teamMapper;

    @Inject
    private TeamRepository teamRepository;


    public TeamDTO findById(UUID id){
        return teamMapper.mapToDTO(teamRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Team with Id "+ id + " not found")));
    }

    public List<TeamDTO> list() {
        return teamMapper.teamsToTeamDTOs(teamRepository.listAll());
    }

    @Transactional
    public TeamDTO update(TeamDTO teamDTO) {
        Team team = teamRepository.findByIdOptional(teamDTO.id())
                .orElseThrow(() -> new NotFoundException("Team with Id "+ teamDTO.id() + " not found"));
        teamRepository.persist(teamMapper.mapToEntity(teamDTO, team));
        return teamMapper.mapToDTO(team);
    }


    @Transactional
    public TeamDTO save(TeamDTO teamDTO){
        Team team = teamMapper.mapToEntity(teamDTO);
        teamRepository.persist(team);
        return teamMapper.mapToDTO(team);
    }

    @Transactional
    public void remove(UUID id) {
        Team team = teamRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Team with Id "+ id + " not found"));
        teamRepository.delete(team);
    }

    @Transactional
    public void clearDatabase() {
        teamRepository.deleteAll();
    }

}
