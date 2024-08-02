package com.ctw.workstation.rest.rack.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel ="cdi")
public interface RackMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "teamId", source = "teamId")
    @Mapping(target = "serialNumber", source = "serialNumber")
    @Mapping(target = "defaultLocation", source = "defaultLocation")
    RackDTO mapToDTO(Rack rack);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "teamId", source = "teamId")
    @Mapping(target = "serialNumber", source = "serialNumber")
    @Mapping(target = "defaultLocation", source = "defaultLocation")
    Rack mapToEntity(RackDTO rackDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "teamId", source = "teamId")
    @Mapping(target = "serialNumber", source = "serialNumber")
    @Mapping(target = "defaultLocation", source = "defaultLocation")
    Rack mapToEntity(RackDTO rackDTO, @MappingTarget Rack rack);

    List<RackDTO> racksToRackDTOs(List<Rack> racks);

}
