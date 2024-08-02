package com.ctw.workstation.rest.team.control;

import com.ctw.workstation.entity.DefaultLocation;
import com.ctw.workstation.infrastructure.exceptions.NotFoundException;
import com.ctw.workstation.rest.team.entity.Team;
import com.ctw.workstation.rest.team.entity.TeamDTO;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@QuarkusTest
class TeamServiceTest {

    @InjectMock
    TeamRepository teamRepository;

    @Inject
    TeamService teamService;

    UUID teamId;
    Team team;

    @BeforeEach
    void setup(){
        teamId = UUID.randomUUID();
        team = new Team();
        team.setId(teamId);
        team.setDefaultLocation(DefaultLocation.LISBON);
        team.setProduct("FYP");
        team.setName("Autobots");
    }

    @Test
    void findById_teamExists_returnsTeamDTO() {
        when(teamRepository.findByIdOptional(teamId)).thenReturn(Optional.of(team));

        TeamDTO result = teamService.findById(teamId);

        assertThat(result.id()).isEqualTo(teamId);
        assertThat(result.name()).isEqualTo( "Autobots");
    }

    @Test
    void findById_teamDoesNotExist_throwsNotFoundException() {
        when(teamRepository.findByIdOptional(teamId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teamService.findById(teamId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Team with Id " + teamId + " not found");
    }

    @Test
    void update_teamExists_updatesAndReturnsUpdatedDTO() {
        when(teamRepository.findByIdOptional(teamId)).thenReturn(Optional.of(team));

        TeamDTO teamDTO = new TeamDTO(teamId, "NYT","TeamName", DefaultLocation.BRAGA);

        TeamDTO updatedTeamDTO = teamService.update(teamDTO);

        assertThat(updatedTeamDTO.name()).isEqualTo("TeamName");
        verify(teamRepository).persist(team);
    }

    @Test
    void update_teamDoesNotExist_throwsNotFoundException() {
        when(teamRepository.findByIdOptional(teamId)).thenReturn(Optional.empty());

        TeamDTO teamDTO = new TeamDTO(teamId, "NYT","TeamName", DefaultLocation.BRAGA);

        assertThatThrownBy(() -> teamService.update(teamDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Team with Id " + teamId + " not found");
    }

    @Test
    void remove_teamExists_deletesTeam() {
        when(teamRepository.findByIdOptional(teamId)).thenReturn(Optional.of(team));

        teamService.remove(teamId);

        verify(teamRepository).delete(team);
    }

    @Test
    void remove_teamDoesNotExist_throwsNotFoundException() {
        when(teamRepository.findByIdOptional(teamId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teamService.remove(teamId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Team with Id " + teamId + " not found");
    }

    @Test
    void save() {
    }
}