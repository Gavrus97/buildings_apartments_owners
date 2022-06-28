package de.telran.buildings_apartments.repository;

import de.telran.buildings_apartments.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    Building findBuildingByHouseAndStreet(String house, String street);

    List<Building> findBuildingsByStreet(String street);

    Boolean existsByStreetAndHouse(String street, String house);

}
