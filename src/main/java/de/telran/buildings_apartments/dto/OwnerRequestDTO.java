package de.telran.buildings_apartments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OwnerRequestDTO {

    private String name;
    private Long apartmentId;
}
