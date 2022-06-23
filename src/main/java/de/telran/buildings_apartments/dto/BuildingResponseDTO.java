package de.telran.buildings_apartments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BuildingResponseDTO {

    private Long id;
    private String address;
    private Integer countApartments;
}
