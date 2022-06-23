package de.telran.buildings_apartments.service.impl;

import de.telran.buildings_apartments.dto.ApartmentRequestDTO;
import de.telran.buildings_apartments.dto.ApartmentResponseDTO;
import de.telran.buildings_apartments.dto.OwnerResponseDTO;
import de.telran.buildings_apartments.entity.Apartment;
import de.telran.buildings_apartments.entity.Owner;
import de.telran.buildings_apartments.repository.ApartmentRepository;
import de.telran.buildings_apartments.repository.OwnerRepository;
import de.telran.buildings_apartments.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentRepository repository;

    @Autowired
    private OwnerRepository ownerRepository;


    @Override
    public void edit(Long apartmentId, ApartmentRequestDTO apartmentDto) {
         Apartment apartment = findApartmentById(apartmentId);
         apartment.setApartmentNumber(apartmentDto.getApartmentNumber());
         apartment.setHasBalcony(apartmentDto.getHasBalcony());
         repository.save(apartment);
    }

    @Override
    public List<ApartmentResponseDTO> getAllApartments() {
        return repository.findAll()
                .stream()
                .map(this::convertApartmentToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ApartmentResponseDTO getApartmentById(Long id) {
        Apartment apartment = findApartmentById(id);
        return convertApartmentToResponseDto(apartment);
    }

    private List<OwnerResponseDTO> getOwnersByApartmentId(Long apartmentId) {
        return ownerRepository.findOwnersByApartmentId(apartmentId)
                .stream()
                .map(this::convertOwnerToDTO)
                .collect(Collectors.toList());

    }

    private Apartment findApartmentById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Apartment with id [%s] doesnt exist", id))
        );
    }

    private OwnerResponseDTO convertOwnerToDTO(Owner owner) {
        return OwnerResponseDTO.builder()
                .name(owner.getName())
                .apartmentId(owner.getApartment().getId())
                .build();
    }

    private ApartmentResponseDTO convertApartmentToResponseDto(Apartment apartment) {
        return ApartmentResponseDTO
                .builder()
                .id(apartment.getId())
                .apartmentNumber(apartment.getApartmentNumber())
                .hasBalcony(apartment.getHasBalcony())
                .owners(getOwnersByApartmentId(apartment.getId()))
                .build();
    }

}
