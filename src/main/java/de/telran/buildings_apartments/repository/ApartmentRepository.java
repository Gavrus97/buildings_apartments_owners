package de.telran.buildings_apartments.repository;

import de.telran.buildings_apartments.entity.Apartment;
import de.telran.buildings_apartments.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    Integer countApartmentsByBuildingId(Long id);

    List<Apartment> findApartmentsByBuilding(Building building);
}
