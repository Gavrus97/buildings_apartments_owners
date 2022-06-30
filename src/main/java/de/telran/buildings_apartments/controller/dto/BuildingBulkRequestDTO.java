package de.telran.buildings_apartments.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BuildingBulkRequestDTO {

    private String street;

    private String house;

    private List<ApartmentBulkRequestDTO> apartments;
}
