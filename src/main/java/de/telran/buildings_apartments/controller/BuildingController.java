package de.telran.buildings_apartments.controller;

import de.telran.buildings_apartments.dto.ApartmentResponseDTO;
import de.telran.buildings_apartments.dto.BuildingRequestDTO;
import de.telran.buildings_apartments.dto.BuildingResponseDTO;
import de.telran.buildings_apartments.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BuildingController {

    @Autowired
    private BuildingService service;

    @PostMapping("/buildings")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam(name = "apartmentsCount") Integer apartmentsCount,
                       @RequestBody BuildingRequestDTO buildingDto) {
        service.create(buildingDto, apartmentsCount);
    }

    @GetMapping("/buildings")
    @ResponseStatus(HttpStatus.OK)
    public List<BuildingResponseDTO> getBuildingsByStreet(@RequestParam(name = "street") String street) {
        return service.getBuildingsByStreet(street);
    }

    @GetMapping("/buildings/{id}/apartments")
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentResponseDTO> getApartmentsOfBuilding(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "hasOwners", required = false) Optional<Boolean> hasOwners) {

        return hasOwners.isPresent() && hasOwners.get() ?
                service.getApartmentsWithOwnersById(id) :
                service.getApartmentsByBuildingId(id);
    }


    @PutMapping("/buildings/{buildingId}/apartments/{apartmentId}/owners/{ownerId}")
    public void moveOwnerToApartment(@PathVariable("buildingId") Long buildingId,
                                     @PathVariable("apartmentId") Long apartmentId,
                                     @PathVariable("ownerId") Long ownerId){
        service.moveAnOwner(buildingId, apartmentId, ownerId);
    }
}
