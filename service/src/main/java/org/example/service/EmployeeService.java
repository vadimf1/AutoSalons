package org.example.service;

import org.example.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    void addEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto getEmployeeById(int id);
    void updateEmployee(EmployeeDto employeeDto);
    void deleteEmployeeById(int id);
}
