package org.example.service;

import org.example.dto.request.EmployeeRequestDto;
import org.example.dto.response.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {
    void addEmployee(EmployeeRequestDto employeeDto);
    List<EmployeeResponseDto> getAllEmployees();
    EmployeeResponseDto getEmployeeById(int id);
    void updateEmployee(int id, EmployeeRequestDto employeeDto);
    void deleteEmployeeById(int id);
}
