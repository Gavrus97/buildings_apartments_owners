package de.telran.buildings_apartments.service;

import de.telran.buildings_apartments.controller.dto.OwnerRequestDTO;
import de.telran.buildings_apartments.controller.dto.OwnerResponseDTO;
import de.telran.buildings_apartments.entity.Owner;

import java.util.List;

public interface OwnerService {

    void create(OwnerRequestDTO ownerDto);

    void edit(Long id, OwnerRequestDTO ownerDto);

    List<OwnerResponseDTO> getAll();

    OwnerResponseDTO getById(Long id);

    List<Long> findOwnersIdsWithApartmentNotNull();

    void saveAll(List<Owner> owners);

}
