package com.ctw.workstation.rest.rackasset.control;

import com.ctw.workstation.infrastructure.exceptions.NotFoundException;
import com.ctw.workstation.rest.rack.control.RackService;
import com.ctw.workstation.rest.rackasset.entity.RackAsset;
import com.ctw.workstation.rest.rackasset.entity.RackAssetDTO;
import com.ctw.workstation.rest.rackasset.entity.RackAssetMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Dependent
public class RackAssetService {

    @Inject
    private RackAssetMapper rackAssetMapper;

    @Inject
    private RackService rackService;

    @Inject
    private RackAssetRepository rackAssetRepository;

    public RackAssetDTO findById(UUID id) {
        return rackAssetMapper.mapToDTO(rackAssetRepository.findByIdOptional(id)
                .orElseThrow(()-> new NotFoundException("RackAsset With Id: " + id + " Not Found")));
    }

    public List<RackAssetDTO> list() {
        return rackAssetMapper.rackAssetsToRackAssetDTOs(rackAssetRepository.listAll());
    }

    @Transactional
    public RackAssetDTO save(RackAssetDTO rackAssetDTO){
        rackService.findById(rackAssetDTO.rackId());
        RackAsset rackAsset = rackAssetMapper.mapToEntity(rackAssetDTO);
        rackAssetRepository.persist(rackAsset);
        return rackAssetMapper.mapToDTO(rackAsset);
    }

    @Transactional
    public RackAssetDTO update(RackAssetDTO rackAssetDTO) {
        RackAsset rackAsset = rackAssetRepository.findByIdOptional(rackAssetDTO.id())
                .orElseThrow(()-> new NotFoundException("RackAsset With Id: " + rackAssetDTO.id() + " Not Found"));
        rackService.findById(rackAssetDTO.rackId());
        rackAssetRepository.persist(rackAssetMapper.mapToEntity(rackAssetDTO, rackAsset));
        return rackAssetMapper.mapToDTO(rackAsset);
    }


    @Transactional
    public void remove(UUID id) {
        RackAsset rackAsset = rackAssetRepository.findByIdOptional(id)
                .orElseThrow(()-> new NotFoundException("RackAsset With Id: " + id + " Not Found"));
        rackAssetRepository.delete(rackAsset);
    }

}
