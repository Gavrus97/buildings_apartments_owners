package de.telran.buildings_apartments.dto;

import de.telran.buildings_apartments.entity.Owner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ApartmentResponseDTO {

    private Long id;
    private Integer apartmentNumber;
    private Boolean hasBalcony;

    @NonNull
    private List<OwnerResponseDTO> owners;
}
