package de.telran.buildings_apartments.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApartmentRequestDTO {

    private Integer apartmentNumber;
    private Boolean hasBalcony;
}
