package de.telran.buildings_apartments.service;

import de.telran.buildings_apartments.controller.dto.BuildingBulkRequestDTO;

import java.util.List;

public interface BuildingBulkService {

    void createBuildingBulk(List<BuildingBulkRequestDTO> buildings);
}
