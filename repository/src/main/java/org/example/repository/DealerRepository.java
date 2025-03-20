package org.example.repository;

import org.example.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DealerRepository extends JpaRepository<Dealer, Integer>, JpaSpecificationExecutor<Dealer> {
    Optional<Dealer> findByName(String name);
}
