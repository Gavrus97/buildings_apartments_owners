package de.telran.buildings_apartments.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "apartment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "apartment_number")
    private Integer apartmentNumber;

    @Column(name = "has_balcony")
    private Boolean hasBalcony;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

}
