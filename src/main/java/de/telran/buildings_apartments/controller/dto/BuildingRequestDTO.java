package de.telran.buildings_apartments.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuildingRequestDTO {

    private String street;
    private String house;
}
