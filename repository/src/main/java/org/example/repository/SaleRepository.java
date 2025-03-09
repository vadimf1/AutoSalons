package org.example.repository;

import org.example.model.Client;
import org.example.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

    List<Sale> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);

    List<Sale> findByClient(Client client);
}
