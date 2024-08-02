package com.ctw.workstation.rest.team.entity;

import com.ctw.workstation.entity.AbstractCreateModify;
import com.ctw.workstation.entity.DefaultLocation;
import com.ctw.workstation.rest.rack.entity.Rack;
import com.ctw.workstation.rest.teammember.entity.TeamMember;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="T_TEAM")
public class Team extends AbstractCreateModify {

    @Column(name="product", nullable = false)
    private String product;
    @Column(name="name", nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name="default_location", nullable = false)
    private DefaultLocation defaultLocation;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> teamMembers;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rack> racks;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DefaultLocation getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(DefaultLocation default_Location) {
        this.defaultLocation = default_Location;
    }

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public List<Rack> getRacks() {
        return racks;
    }

    public void setRacks(List<Rack> racks) {
        this.racks = racks;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", default_Location=" + defaultLocation + '\'' +
                "product='" + product + '\'' +
                ", Modify at='" + getModified_at() + '\'' +
                ", Created at=" + getCreated_at() +
                '}';
    }
}
