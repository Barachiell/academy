package com.ctw.workstation.rest.teammember.control;

import com.ctw.workstation.rest.teammember.entity.TeamMember;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class TeamMemberRepository implements PanacheRepositoryBase<TeamMember, UUID> {
}
