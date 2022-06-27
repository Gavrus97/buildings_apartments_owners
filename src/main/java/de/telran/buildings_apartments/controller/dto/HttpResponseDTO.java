package de.telran.buildings_apartments.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class HttpResponseDTO {

    private String message;
    private HttpStatus status;
}
