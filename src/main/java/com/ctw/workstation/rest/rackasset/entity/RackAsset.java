package com.ctw.workstation.rest.rackasset.entity;

import com.ctw.workstation.entity.AbstractEntity;
import com.ctw.workstation.rest.rack.entity.Rack;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="T_RACK_ASSET")
public class RackAsset extends AbstractEntity {

    @Column(name="asset_tag", nullable = false)
    private String assetTag;
    @Column(name="rack_id")
    private UUID rackId;

    @JoinColumn(name="rack_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Rack.class, fetch = FetchType.LAZY)
    private Rack rack;

    public String getAssetTag() {
        return assetTag;
    }

    public void setAssetTag(String asset_tag) {
        this.assetTag = asset_tag;
    }

    public UUID getRackId() {
        return rackId;
    }

    public void setRackId(UUID rack_id) {
        this.rackId = rack_id;
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

}
