package com.ctw.workstation.rest.teammember.control;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.ctw.workstation.infrastructure.exceptions.NotFoundException;
import com.ctw.workstation.rest.team.control.TeamRepository;
import com.ctw.workstation.rest.team.entity.Team;
import com.ctw.workstation.rest.teammember.entity.TeamMember;
import com.ctw.workstation.rest.teammember.entity.TeamMemberDTO;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TeamMemberServiceTest {

    @InjectMock
    TeamMemberRepository teamMemberRepository;

    @InjectMock
    TeamRepository teamRepository;

    @Inject
    TeamMemberService teamMemberService;

    UUID teamMemberId;
    UUID teamId;
    TeamMember teamMember;

    @BeforeEach
    void setUp() {
        teamMemberId = UUID.randomUUID();
        teamId = UUID.randomUUID();

        teamMember = new TeamMember();
        teamMember.setId(teamMemberId);
        teamMember.setName("Bernard");
        teamMember.setCtwId("CTW54321");
        teamMember.setTeamId(teamId);
    }

    @Test
    void createTeamMember_teamExists_createsAndReturnsTeamMemberDTO() {
        when(teamRepository.findByIdOptional(teamId)).thenReturn(Optional.of(new Team()));
        //when(teamMemberRepository.persist(teamMember)).thenReturn(teamMember);

        TeamMemberDTO teamMemberDTO = new TeamMemberDTO(teamMemberId, teamId,"Bernard","CTW12345");

        TeamMemberDTO result = teamMemberService.save(teamMemberDTO);

        assertThat(result.id()).isEqualTo(teamMemberId);
        assertThat(result.name()).isEqualTo("Bernard");
        assertThat(result.teamId()).isEqualTo(teamId);
    }

    @Test
    void createTeamMember_teamDoesNotExist_throwsNotFoundException() {
        when(teamRepository.findByIdOptional(teamId)).thenReturn(Optional.empty());

        TeamMemberDTO teamMemberDTO = new TeamMemberDTO(teamMemberId, teamId,"Bernard","CTW12345");

        assertThatThrownBy(() -> teamMemberService.save(teamMemberDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Team with Id " + teamId + " not found");
    }

    @Test
    void updateTeamMember_teamMemberAndTeamExist_updatesAndReturnsUpdatedDTO() {
        when(teamMemberRepository.findByIdOptional(teamMemberId)).thenReturn(Optional.of(teamMember));
        when(teamRepository.findByIdOptional(teamId)).thenReturn(Optional.of(new Team()));

        TeamMemberDTO teamMemberDTO = new TeamMemberDTO(teamMemberId, teamId,"Updated Member","CTW12345");

        TeamMemberDTO updatedTeamMemberDTO = teamMemberService.update(teamMemberDTO);

        assertThat(updatedTeamMemberDTO.name()).isEqualTo("Updated Member");
        verify(teamMemberRepository).persist(teamMember);
    }

    @Test
    void updateTeamMember_teamMemberDoesNotExist_throwsNotFoundException() {
        when(teamMemberRepository.findByIdOptional(teamMemberId)).thenReturn(Optional.empty());

        TeamMemberDTO teamMemberDTO = new TeamMemberDTO(teamMemberId, teamId,"Bernard","CTW12345");

        assertThatThrownBy(() -> teamMemberService.update(teamMemberDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("TeamMember with Id " + teamMemberId + " not found");
    }

    @Test
    void updateTeamMember_teamDoesNotExist_throwsNotFoundException() {
        when(teamMemberRepository.findByIdOptional(teamMemberId)).thenReturn(Optional.of(teamMember));
        when(teamRepository.findByIdOptional(teamId)).thenReturn(Optional.empty());

        TeamMemberDTO teamMemberDTO = new TeamMemberDTO(teamMemberId, teamId,"Bernard","CTW12345");

        assertThatThrownBy(() -> teamMemberService.update(teamMemberDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Team with Id " + teamId + " not found");
    }

    @Test
    void removeTeamMember_teamMemberExists_deletesTeamMember() {
        when(teamMemberRepository.findByIdOptional(teamMemberId)).thenReturn(Optional.of(teamMember));

        teamMemberService.remove(teamMemberId);

        verify(teamMemberRepository).delete(teamMember);
    }

    @Test
    void removeTeamMember_teamMemberDoesNotExist_throwsNotFoundException() {
        when(teamMemberRepository.findByIdOptional(teamMemberId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teamMemberService.remove(teamMemberId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("TeamMember with Id " + teamMemberId + " not found");
    }

}