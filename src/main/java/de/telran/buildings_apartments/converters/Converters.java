package de.telran.buildings_apartments.converters;

import de.telran.buildings_apartments.controller.dto.*;
import de.telran.buildings_apartments.entity.Apartment;
import de.telran.buildings_apartments.entity.Building;
import de.telran.buildings_apartments.entity.Owner;

import java.util.List;
import java.util.stream.Collectors;

public class Converters {

    public static Building convertToEntityBuilding(BuildingRequestDTO buildingDto) {
        return Building.builder()
                .house(buildingDto.getHouse())
                .street(buildingDto.getStreet())
                .build();
    }


    public static Building convertBulkDtoToBuilding(BuildingBulkRequestDTO building) {
        return Building
                .builder()
                .house(building.getHouse())
                .street(building.getStreet())
                .build();
    }

    public static Apartment convertToApartmentEntity(ApartmentBulkRequestDTO dto, Building building) {
        return Apartment.builder()
                .apartmentNumber(dto.getApartmentNumber())
                .hasBalcony(dto.getHasBalcony())
                .building(building)
                .build();
    }

    public static List<Owner> convertBulkToOwners(List<OwnerBulkRequestDTO> dtos, Apartment apartment){
        return dtos
                .stream()
                .map(owner -> Owner
                        .builder()
                        .name(owner.getName())
                        .apartment(apartment)
                        .build()
                )
                .collect(Collectors.toList());
    }
}
