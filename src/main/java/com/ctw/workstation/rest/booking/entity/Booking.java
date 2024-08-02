package com.ctw.workstation.rest.booking.entity;

import com.ctw.workstation.entity.AbstractCreateModify;
import com.ctw.workstation.rest.rack.entity.Rack;
import com.ctw.workstation.rest.teammember.entity.TeamMember;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name= "T_BOOKING")
public class Booking extends AbstractCreateModify {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="book_from", nullable = false)
    private LocalDateTime bookFrom;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="book_to", nullable = false)
    private LocalDateTime bookTo;
    @Column(name="requester_id", nullable = false)
    private UUID requesterId;
    @Column(name="rack_id", nullable = false)
    private UUID rackId;

    @JoinColumn(name="requester_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = TeamMember.class, fetch = FetchType.LAZY)
    private TeamMember teamMember;

    @JoinColumn(name="rack_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Rack.class, fetch = FetchType.LAZY)
    private Rack rack;

    public void setBookFrom(LocalDateTime book_from) {
        this.bookFrom = book_from;
    }

    public void setBookTo(LocalDateTime book_to) {
        this.bookTo = book_to;
    }

    public void setRequesterId(UUID requester_id) {
        this.requesterId = requester_id;
    }

    public void setRackId(UUID rack_id) {
        this.rackId = rack_id;
    }

    public LocalDateTime getBookFrom() {
        return bookFrom;
    }

    public LocalDateTime getBookTo() {
        return bookTo;
    }

    public UUID getRequesterId() {
        return requesterId;
    }

    public UUID getRackId() {
        return rackId;
    }

    public TeamMember getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(TeamMember teamMember) {
        this.teamMember = teamMember;
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

}
