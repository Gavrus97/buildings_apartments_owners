package de.telran.buildings_apartments.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "building")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private String house;

}
