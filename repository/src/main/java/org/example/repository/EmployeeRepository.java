package org.example.repository;

import org.example.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByPosition(String position);

    List<Employee> findBySalaryGreaterThan(double salary);
}
