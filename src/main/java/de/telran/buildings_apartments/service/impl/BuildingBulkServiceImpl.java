package de.telran.buildings_apartments.service.impl;

import de.telran.buildings_apartments.controller.dto.BuildingBulkRequestDTO;
import de.telran.buildings_apartments.entity.Building;
import de.telran.buildings_apartments.repository.BuildingRepository;
import de.telran.buildings_apartments.service.ApartmentBulkService;
import de.telran.buildings_apartments.service.BuildingBulkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BuildingBulkServiceImpl implements BuildingBulkService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ApartmentBulkService apartmentBulkService;

    @Override
    @Transactional
    public void createBuildingBulk(List<BuildingBulkRequestDTO> buildings) {
        buildings.stream()
                .filter(x -> !buildingRepository.existsByStreetAndHouse(x.getStreet(), x.getHouse()))
                .forEach(buildingDTO -> {
                    Building building = Building.builder()
                            .street(buildingDTO.getStreet())
                            .house(buildingDTO.getHouse())
                            .build();
                    buildingRepository.save(building);


                    var apartmentsDTO = buildingDTO.getApartments();

                    if (apartmentsDTO == null || apartmentsDTO.isEmpty()) {
                        return;
                    }

                    apartmentsDTO
                            .forEach(apartmentDTO -> {
                                apartmentBulkService.createApartmentAndOwners(apartmentDTO, building);
                            });
                });
    }
}
