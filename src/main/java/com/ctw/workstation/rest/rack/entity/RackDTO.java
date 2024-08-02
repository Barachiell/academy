package com.ctw.workstation.rest.rack.entity;

import com.ctw.workstation.entity.DefaultLocation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RackDTO(UUID id, @NotNull UUID teamId , @NotNull String serialNumber, @NotNull Rack.Status status, @NotNull DefaultLocation defaultLocation) {

}
