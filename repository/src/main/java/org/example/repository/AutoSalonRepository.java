package org.example.repository;

import org.example.model.AutoSalon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AutoSalonRepository extends JpaRepository<AutoSalon, Integer>, JpaSpecificationExecutor<AutoSalon> {
}
