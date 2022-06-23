package de.telran.buildings_apartments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OwnerResponseDTO {

    private String name;
    private Long apartmentId;
}
