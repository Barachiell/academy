package com.ctw.workstation.rest.team.entity;

import com.ctw.workstation.entity.DefaultLocation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public record TeamDTO(UUID id, @NotNull String product, @NotNull String name, @NotNull DefaultLocation defaultLocation) {

}
