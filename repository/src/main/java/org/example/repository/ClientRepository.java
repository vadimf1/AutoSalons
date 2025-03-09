package org.example.repository;

import org.example.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByPassportNumber(String passportNumber);

    List<Client> findByBirthDate(LocalDate birthDate);

    Optional<Client> findByUserId(Integer userId);
}
