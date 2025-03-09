package org.example.repository;

import org.example.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByCity(String city);

    @Query("SELECT DISTINCT a.country FROM Address a")
    List<String> findAllCountries();

    List<Address> findByPostalCode(String postalCode);

    List<Address> findByCountryAndCity(String country, String city);
}
