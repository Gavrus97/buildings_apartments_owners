package de.telran.buildings_apartments.service.impl;

import de.telran.buildings_apartments.dto.ApartmentResponseDTO;
import de.telran.buildings_apartments.dto.BuildingRequestDTO;
import de.telran.buildings_apartments.dto.BuildingResponseDTO;
import de.telran.buildings_apartments.dto.OwnerResponseDTO;
import de.telran.buildings_apartments.entity.Apartment;
import de.telran.buildings_apartments.entity.Building;
import de.telran.buildings_apartments.entity.Owner;
import de.telran.buildings_apartments.repository.ApartmentRepository;
import de.telran.buildings_apartments.repository.BuildingRepository;
import de.telran.buildings_apartments.repository.OwnerRepository;
import de.telran.buildings_apartments.service.BuildingService;
import de.telran.buildings_apartments.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository repository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerService ownerService;

    @Override
    public void create(BuildingRequestDTO buildingDto, int apartmentsCount) {
        if (repository.findBuildingByHouseAndStreet(buildingDto.getHouse(), buildingDto.getStreet()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("The building with number '%s' on the ", buildingDto.getHouse())
                            + String.format("'%s' street already exists!", buildingDto.getStreet()));
        }

        repository.save(convertToEntityBuilding(buildingDto));

        Building building = repository
                .findBuildingByHouseAndStreet(buildingDto.getHouse(), buildingDto.getStreet());

        for (int i = 0; i < apartmentsCount; i++) {
            apartmentRepository.save(Apartment.builder()
                    .building(building)
                    .build());
        }
    }

    @Override
    public List<BuildingResponseDTO> getBuildingsByStreet(String street) {
        return repository.findBuildingsByStreet(street)
                .stream()
                .map(this::convertBuildingToDto)
                .collect(Collectors.toList());
    }

    //???????????????????????????????????????????
    @Override
    public List<ApartmentResponseDTO> getApartmentsWithOwnersById(Long buildingId) {
        return apartmentRepository
                .findAllByBuildingIdAndIdIsIn(
                        buildingId,
                        ownerService.findOwnersIdsWithApartmentNotNull())
                .stream()
                .map(this::convertApartmentToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentResponseDTO> getApartmentsByBuildingId(Long buildingId) {

        Building building = findBuildingById(buildingId); // для респонса not found

        return apartmentRepository.findApartmentsByBuilding(building)
                .stream()
                .map(this::convertApartmentToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void moveAnOwner(Long buildingId, Long apartmentId, Long ownerId) {

        Building building = findBuildingById(buildingId); // для not found
        Apartment apartment = getApartmentByApartmentId(apartmentId); // для not fount

        if (apartment.getBuilding().getId().equals(buildingId)) {
            Owner owner = getOwnerByOwnerId(ownerId);
            owner.setApartment(apartment);
            ownerRepository.save(owner);
        } else
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("The apartment with id [%s] isn't in the building with id ", apartmentId)
                            + String.format("[%s]", buildingId));
    }

    @Override
    public void evictOwnerFromApartment(Long buildingId, Long apartmentId, Long ownerId) {

        Owner owner = ownerRepository.
                findOwnerByIdAndApartmentId(
                        ownerId,
                        apartmentRepository.findApartmentByIdAndBuildingId(
                                        apartmentId,
                                        findBuildingById(buildingId).getId())
                                .getId()
                );
        owner.setApartment(null);
        ownerRepository.save(owner);


//        Building building = findBuildingById(buildingId); // для not found
//        Apartment apartment = getApartmentByApartmentId(apartmentId); // для not fount
//
//        if (apartment.getBuilding().getId().equals(buildingId)) {
//            Owner owner = getOwnerByOwnerId(ownerId);
//
//            if (owner.getApartment() == null) {
//                throw new ResponseStatusException(HttpStatus.CONFLICT,
//                        String.format("The owner with id [%s] has no apartment !", ownerId));
//            } else if (owner.getApartment().getId().equals(apartmentId)) {
//                owner.setApartment(null);
//                ownerRepository.save(owner);
//            } else
//                throw new ResponseStatusException(HttpStatus.CONFLICT,
//                        String.format("The owner with id [%s] isn't an owner of the apartment with id ", ownerId)
//                                + String.format("[%s]", apartmentId));
//
//        } else
//            throw new ResponseStatusException(HttpStatus.CONFLICT,
//                    String.format("The apartment with id [%s] isn't in the building with id ", apartmentId)
//                            + String.format("[%s]", buildingId));
    }

    @Override
    @Transactional
    public void deleteBuilding(Long buildingId) {
        Building building = findBuildingById(buildingId);

        List<Apartment> apartments = apartmentRepository.findApartmentsByBuildingId(buildingId);
        List<Long> apartmentsId = apartments
                .stream()
                .map(Apartment::getId)
                .collect(Collectors.toList());

        List<Long> ownersId = ownerRepository.findOwnersByApartmentIsIn(apartments)
                .stream()
                .map(Owner::getId)
                .collect(Collectors.toList());

        ownerRepository.deleteAllByIdIsIn(ownersId);
        apartmentRepository.deleteApartmentsByIdIsIn(apartmentsId);

        repository.delete(building);
    }

    private Apartment getApartmentByApartmentId(Long id) {
        return apartmentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Apartment with id [%s] doesnt exist", id))
        );
    }

    private Owner getOwnerByOwnerId(Long id) {
        return ownerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("There is no owner with id [%s]", id))
        );
    }

    private Building findBuildingById(Long buildingId) {
        return repository.findById(buildingId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("The building with id [%s] doesn't exist", buildingId))
        );
    }

    private Building convertToEntityBuilding(BuildingRequestDTO buildingDto) {
        return Building.builder()
                .house(buildingDto.getHouse())
                .street(buildingDto.getStreet())
                .build();
    }

    private BuildingResponseDTO convertBuildingToDto(Building building) {
        return BuildingResponseDTO.builder()
                .address(building.getStreet() + " " + building.getHouse())
                .countApartments(countApartmentsById(building.getId()))
                .id(building.getId())
                .build();
    }

    private Integer countApartmentsById(Long id) {
        return apartmentRepository.countApartmentsByBuildingId(id);
    }

    private ApartmentResponseDTO convertApartmentToDto(Apartment apartment) {
        return ApartmentResponseDTO
                .builder()
                .apartmentNumber(apartment.getApartmentNumber())
                .id(apartment.getId())
                .hasBalcony(apartment.getHasBalcony())
                .owners(convertListOwnersToListDto(
                        ownerRepository.findOwnersByApartmentId(apartment.getId())))
                .build();
    }

    private List<OwnerResponseDTO> convertListOwnersToListDto(List<Owner> owners) {
        return owners
                .stream()
                .map(this::convertOwnerToDto)
                .collect(Collectors.toList());
    }

    private OwnerResponseDTO convertOwnerToDto(Owner owner) {
        return OwnerResponseDTO
                .builder()
                .name(owner.getName())
                .apartmentId(owner.getApartment().getId())
                .build();
    }
}
