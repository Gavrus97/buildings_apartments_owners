package de.telran.buildings_apartments.service.impl;

import de.telran.buildings_apartments.controller.dto.ApartmentBulkRequestDTO;
import de.telran.buildings_apartments.entity.Apartment;
import de.telran.buildings_apartments.entity.Building;
import de.telran.buildings_apartments.entity.Owner;
import de.telran.buildings_apartments.repository.ApartmentRepository;
import de.telran.buildings_apartments.repository.OwnerRepository;
import de.telran.buildings_apartments.service.ApartmentBulkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApartmentBulkServiceImpl implements ApartmentBulkService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createApartmentAndOwners(ApartmentBulkRequestDTO apartmentDTO, Building building) {

        var apartment = Apartment.builder()
                .building(building)
                .hasBalcony(apartmentDTO.getHasBalcony())
                .apartmentNumber(apartmentDTO.getApartmentNumber())
                .build();
        apartmentRepository.save(apartment);

        var ownersDTO = apartmentDTO.getOwners();

        if (ownersDTO == null || ownersDTO.isEmpty()) {
            return;
        }

        ownersDTO
                .forEach(ownerDTO -> {
                    var owner = Owner.builder()
                            .name(ownerDTO.getName())
                            .apartment(apartment)
                            .build();
                    ownerRepository.save(owner);
                });
    }
}
