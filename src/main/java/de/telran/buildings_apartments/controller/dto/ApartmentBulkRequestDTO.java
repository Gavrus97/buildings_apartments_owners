package de.telran.buildings_apartments.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ApartmentBulkRequestDTO {

    private Integer apartmentNumber;

    private Boolean hasBalcony;

    private List<OwnerBulkRequestDTO> owners;
}
