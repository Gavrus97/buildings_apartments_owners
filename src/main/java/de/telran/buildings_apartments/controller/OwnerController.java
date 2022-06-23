package de.telran.buildings_apartments.controller;

import de.telran.buildings_apartments.dto.OwnerRequestDTO;
import de.telran.buildings_apartments.dto.OwnerResponseDTO;
import de.telran.buildings_apartments.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OwnerController {

    @Autowired
    private OwnerService service;

    @PostMapping("/owners")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody OwnerRequestDTO ownerDto){
        service.create(ownerDto);
    }

    @PutMapping("/owners/{id}")
    public void edit(@PathVariable("id") Long id,
                     @RequestBody OwnerRequestDTO ownerDto){
        service.edit(id, ownerDto);
    }

    @GetMapping("/owners")
    public List<OwnerResponseDTO> getAll(){
        return service.getAll();
    }

    @GetMapping("/owners/{id}")
    public OwnerResponseDTO getById(@PathVariable("id") Long id){
        return service.getById(id);
    }
}
