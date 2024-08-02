package com.ctw.workstation.rest.teammember.entity;

import com.ctw.workstation.entity.AbstractCreateModify;
import com.ctw.workstation.rest.booking.entity.Booking;
import com.ctw.workstation.rest.team.entity.Team;
import jakarta.persistence.*;

import java.awt.print.Book;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="T_TEAM_MEMBER")
public class TeamMember extends AbstractCreateModify {

    @Column(name="team_id", nullable = false)
    private UUID teamId;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="ctw_id", nullable = false)
    private String ctwId;

    @JoinColumn(name="team_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
    private Team team;


    @OneToMany(mappedBy = "teamMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public String getCtwId() {
        return ctwId;
    }

    public void setCtwId(String ctw_id) {
        this.ctwId = ctw_id;
    }

}
