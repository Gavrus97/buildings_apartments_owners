package de.telran.buildings_apartments.service;

import de.telran.buildings_apartments.controller.dto.ApartmentBulkRequestDTO;
import de.telran.buildings_apartments.entity.Building;

public interface ApartmentBulkService {

    void createApartmentAndOwners(ApartmentBulkRequestDTO apartmentDTO, Building building);

}
