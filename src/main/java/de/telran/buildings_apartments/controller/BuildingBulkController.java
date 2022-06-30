package de.telran.buildings_apartments.controller;

import de.telran.buildings_apartments.controller.dto.BuildingBulkRequestDTO;
import de.telran.buildings_apartments.service.BuildingBulkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BuildingBulkController {

    @Autowired
    private BuildingBulkService service;

    @PostMapping("/buildings/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public void createACity(@RequestBody List<BuildingBulkRequestDTO> buildings){
        service.createBuildingBulk(buildings);
    }
}
