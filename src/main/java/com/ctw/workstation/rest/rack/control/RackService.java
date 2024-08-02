package com.ctw.workstation.rest.rack.control;

import com.ctw.workstation.infrastructure.exceptions.ConflictException;
import com.ctw.workstation.infrastructure.exceptions.NotFoundException;
import com.ctw.workstation.rest.rack.entity.Rack;
import com.ctw.workstation.rest.rack.entity.RackDTO;
import com.ctw.workstation.rest.rack.entity.RackMapper;
import com.ctw.workstation.rest.team.control.TeamService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Dependent
public class RackService {

    @Inject
    private RackMapper rackMapper;

    @Inject
    private TeamService teamService;

    @Inject
    private RackRepository rackRepository;

    public RackDTO findById(UUID id) {
        return rackMapper.mapToDTO(rackRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Rack with id "+ id + " not found")));
    }

    public List<RackDTO> list() {
        return rackMapper.racksToRackDTOs(rackRepository.listAll());
    }

    public List<RackDTO> listByStatus(Rack.Status status) {
        return rackRepository.listAll().stream().filter(rack -> rack.getStatus().equals(status))
                .map(rack -> rackMapper.mapToDTO(rack))
                .toList();
    }

    @Transactional
    public RackDTO save(RackDTO rackDTO){
        teamService.findById(rackDTO.teamId());
        Rack rack = rackMapper.mapToEntity(rackDTO);
        rackRepository.persist(rack);
        return rackMapper.mapToDTO(rack);
    }

    @Transactional
    public RackDTO update(RackDTO rackDTO) {
        Rack rack = rackRepository.findByIdOptional(rackDTO.id())
                .orElseThrow(()-> new NotFoundException("Rack With Id: " + rackDTO.id() + " Not Found"));
        teamService.findById(rackDTO.teamId());
        rackRepository.persist(rackMapper.mapToEntity(rackDTO, rack));
        return rackMapper.mapToDTO(rack);
    }


    @Transactional
    public void remove(UUID id) {
        Rack rack = rackRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Rack With Id: " + id + " Not Found"));
        rackRepository.delete(rack);
    }

    public void updateRackStatus(UUID id, Rack.Status status){
        Rack rack = rackRepository.findById(id);
        rack.setStatus(status);
        RackDTO rackDTO = rackMapper.mapToDTO(rack);
        update(rackDTO);
    }

    public void isRackAvailable(RackDTO rackDTO){
        if(rackDTO.status() != Rack.Status.AVAILABLE){
            throw new ConflictException("Rack With Id " + rackDTO.id() + " is UNAVAILABLE");
        }
    }

    @Transactional
    public void clearDatabase() {
        rackRepository.deleteAll();
    }


}
