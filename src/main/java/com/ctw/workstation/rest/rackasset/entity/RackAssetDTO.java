package com.ctw.workstation.rest.rackasset.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RackAssetDTO(UUID id, @NotNull UUID rackId, @NotNull String assetTag) {

}
