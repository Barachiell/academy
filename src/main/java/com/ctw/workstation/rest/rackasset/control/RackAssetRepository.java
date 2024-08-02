package com.ctw.workstation.rest.rackasset.control;

import com.ctw.workstation.rest.rackasset.entity.RackAsset;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class RackAssetRepository implements PanacheRepositoryBase<RackAsset, UUID> {
}
