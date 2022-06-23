package de.telran.buildings_apartments.service;

import de.telran.buildings_apartments.dto.ApartmentRequestDTO;
import de.telran.buildings_apartments.dto.ApartmentResponseDTO;

import java.util.List;

public interface ApartmentService {

    void edit(Long apartmentId, ApartmentRequestDTO apartmentDto);

    List<ApartmentResponseDTO> getAllApartments();

    ApartmentResponseDTO getApartmentById(Long id);
}
