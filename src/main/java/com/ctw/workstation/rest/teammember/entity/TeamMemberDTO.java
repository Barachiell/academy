package com.ctw.workstation.rest.teammember.entity;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TeamMemberDTO(UUID id, @NotNull UUID teamId,  @NotNull String name, @NotNull String ctwId) {

}
