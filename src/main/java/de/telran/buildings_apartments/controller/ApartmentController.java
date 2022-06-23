package de.telran.buildings_apartments.controller;

import de.telran.buildings_apartments.dto.ApartmentRequestDTO;
import de.telran.buildings_apartments.dto.ApartmentResponseDTO;
import de.telran.buildings_apartments.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApartmentController {

    @Autowired
    private ApartmentService service;

    @GetMapping("/apartments")
    public List<ApartmentResponseDTO> getAll() {
        return service.getAllApartments();
    }

    @GetMapping("/apartments/{id}")
    public ApartmentResponseDTO getById(@PathVariable("id") Long id) {
        return service.getApartmentById(id);
    }

    @PutMapping("/apartments/{id}")
    public void edit(@PathVariable("id") Long id,
                     @RequestBody ApartmentRequestDTO apartmentDto) {
        service.edit(id, apartmentDto);
    }
}
