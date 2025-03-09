package org.example.repository;

import org.example.model.Address;
import org.example.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DealerRepository extends JpaRepository<Dealer, Integer> {
    Optional<Dealer> findByName(String name);

    List<Dealer> findByAddress(Address address);
}
