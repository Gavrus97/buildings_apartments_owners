package de.telran.buildings_apartments.service;

import de.telran.buildings_apartments.dto.ApartmentResponseDTO;
import de.telran.buildings_apartments.dto.BuildingRequestDTO;
import de.telran.buildings_apartments.dto.BuildingResponseDTO;
import de.telran.buildings_apartments.entity.Apartment;
import de.telran.buildings_apartments.entity.Building;

import java.util.List;
import java.util.Optional;

public interface BuildingService {

    void create(BuildingRequestDTO buildingDto, int apartmentsCount);

    List<BuildingResponseDTO> getBuildingsByStreet(String street);

    List<ApartmentResponseDTO> getApartmentsWithOwnersById(Long buildingId);

    List<ApartmentResponseDTO> getApartmentsByBuildingId(Long buildingId);

    void moveAnOwner(Long buildingId, Long apartmentId, Long ownerId);

    void evictOwnerFromApartment(Long buildingId, Long apartmentId, Long ownerId);

    void deleteBuilding(Long buildingId);

}
