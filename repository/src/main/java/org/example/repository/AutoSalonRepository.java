package org.example.repository;

import org.example.model.AutoSalon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutoSalonRepository extends JpaRepository<AutoSalon, Integer> {
    Optional<AutoSalon> findByAddressId(int addressId);

    Optional<AutoSalon> findByName(String name);

    List<AutoSalon> findByNameContaining(String name);
}
