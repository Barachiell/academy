package com.ctw.workstation.rest.rack.entity;

import com.ctw.workstation.entity.AbstractCreateModify;
import com.ctw.workstation.entity.DefaultLocation;
import com.ctw.workstation.rest.booking.entity.Booking;
import com.ctw.workstation.rest.rackasset.entity.RackAsset;
import com.ctw.workstation.rest.team.entity.Team;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="T_RACK")
public class Rack extends AbstractCreateModify {

    @Column(name="serial_number", nullable = false)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private Status status;

    @Column(name="team_id", nullable = false)
    private UUID teamId;

    @Enumerated(EnumType.STRING)
    @Column(name="default_location", nullable = false)
    private DefaultLocation defaultLocation;

    @JoinColumn(name="team_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
    private Team team;

    @OneToMany(mappedBy = "rack", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RackAsset> rackAssets;

    @OneToMany(mappedBy = "rack", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public DefaultLocation getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(DefaultLocation default_location) {
        this.defaultLocation = default_location;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<RackAsset> getRackAssets() {
        return rackAssets;
    }

    public void setRackAssets(List<RackAsset> rackAssets) {
        this.rackAssets = rackAssets;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }


    public enum Status {

        AVAILABLE,
        BOOKED,
        UNAVAILABLE

    }

}
