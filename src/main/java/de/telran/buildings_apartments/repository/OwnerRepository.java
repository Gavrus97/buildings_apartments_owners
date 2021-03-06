package de.telran.buildings_apartments.repository;

import de.telran.buildings_apartments.entity.Apartment;
import de.telran.buildings_apartments.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    List<Owner> findOwnersByApartmentId(Long id);

    List<Owner> findOwnersByApartmentIsNotNull();

    Owner findOwnerByIdAndApartmentId(Long ownerId, Long apartmentId);

    List<Owner> findOwnersByApartmentIsIn(List<Apartment> apartments);

}
