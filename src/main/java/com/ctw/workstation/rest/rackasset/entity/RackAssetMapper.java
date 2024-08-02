package com.ctw.workstation.rest.rackasset.entity;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel ="cdi")
public interface RackAssetMapper{

    @Mapping(target = "id", source = "id")
    @Mapping(target = "rackId", source = "rackId")
    @Mapping(target = "assetTag", source = "assetTag")
    RackAssetDTO mapToDTO(RackAsset rackAsset);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "rackId", source = "rackId")
    @Mapping(target = "assetTag", source = "assetTag")
    RackAsset mapToEntity(RackAssetDTO rackAssetDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "rackId", source = "rackId")
    @Mapping(target = "assetTag", source = "assetTag")
    RackAsset mapToEntity(RackAssetDTO rackAssetDTO, @MappingTarget RackAsset rackAsset);

    List<RackAssetDTO> rackAssetsToRackAssetDTOs(List<RackAsset> rackAssets);

}
