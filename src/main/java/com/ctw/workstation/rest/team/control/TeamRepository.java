package com.ctw.workstation.rest.team.control;

import com.ctw.workstation.rest.team.entity.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class TeamRepository implements PanacheRepositoryBase<Team, UUID> {
}
