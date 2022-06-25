package de.telran.buildings_apartments.service.impl;

import de.telran.buildings_apartments.dto.OwnerRequestDTO;
import de.telran.buildings_apartments.dto.OwnerResponseDTO;
import de.telran.buildings_apartments.entity.Owner;
import de.telran.buildings_apartments.repository.ApartmentRepository;
import de.telran.buildings_apartments.repository.OwnerRepository;
import de.telran.buildings_apartments.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository repository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public List<Long> findOwnersIdsWithApartmentNotNull() {
        return repository.findOwnersByApartmentIsNotNull()
                .stream()
                .map(x -> x.getApartment().getId())
                .collect(Collectors.toList());
    }

    @Override
    public void create(OwnerRequestDTO ownerDto) {
        repository.save(convertToOwnerEntity(ownerDto));
    }

    @Override
    public void edit(Long id, OwnerRequestDTO ownerDto) {
        Owner owner = findOwnerById(id);
        owner.setName(ownerDto.getName());
        repository.save(owner);
    }

    @Override
    public List<OwnerResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OwnerResponseDTO getById(Long id) {
        return convertToDTO(findOwnerById(id));
    }



    private Owner convertToOwnerEntity(OwnerRequestDTO ownerDto) {
        return Owner.builder()
                .name(ownerDto.getName())
                .apartment(apartmentRepository.findById(ownerDto.getApartmentId()).orElseThrow())
                .build();
    }

    private Owner findOwnerById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Owner with id [%s] doesn't exist!", id))
        );
    }

    private OwnerResponseDTO convertToDTO(Owner owner){
        return OwnerResponseDTO.builder()
                .name(owner.getName())
                .build();
    }
}
