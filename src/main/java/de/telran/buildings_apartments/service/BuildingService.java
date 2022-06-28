package de.telran.buildings_apartments.service;

import de.telran.buildings_apartments.controller.dto.ApartmentResponseDTO;
import de.telran.buildings_apartments.controller.dto.BuildingRequestDTO;
import de.telran.buildings_apartments.controller.dto.BuildingResponseDTO;
import de.telran.buildings_apartments.controller.dto.BuildingBulkRequestDTO;

import java.util.List;

public interface BuildingService {

    void create(BuildingRequestDTO buildingDto, int apartmentsCount);

    List<BuildingResponseDTO> getBuildingsByStreet(String street);

    List<ApartmentResponseDTO> getApartmentsWithOwnersById(Long buildingId);

    List<ApartmentResponseDTO> getApartmentsByBuildingId(Long buildingId);

    void moveAnOwner(Long buildingId, Long apartmentId, Long ownerId);

    void evictOwnerFromApartment(Long buildingId, Long apartmentId, Long ownerId);

    void deleteBuilding(Long buildingId);

    void createACity(List<BuildingBulkRequestDTO> city);

}
