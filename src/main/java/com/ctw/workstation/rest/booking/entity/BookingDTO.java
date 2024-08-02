package com.ctw.workstation.rest.booking.entity;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;


public record BookingDTO(UUID id, @NotNull LocalDateTime bookFrom, @NotNull LocalDateTime bookTo, @NotNull UUID requesterId, @NotNull UUID rackId) {





}
