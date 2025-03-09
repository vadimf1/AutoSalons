package org.example.repository;

import org.example.model.TestDrive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TestDriveRepository extends JpaRepository<TestDrive, Integer> {

    List<TestDrive> findByClient_Id(Integer clientId);

    List<TestDrive> findByTestDriveDate(LocalDate date);
}
