package com.ctw.workstation.rest.rack.control;

import com.ctw.workstation.rest.rack.entity.Rack;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class RackRepository implements PanacheRepositoryBase<Rack, UUID> {
}
