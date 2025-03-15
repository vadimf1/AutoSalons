package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.aop.Loggable;
import org.example.dto.request.EmployeeRequestDto;
import org.example.dto.response.EmployeeResponseDto;
import org.example.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable int id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Loggable
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeRequestDto employeeDto) {
        employeeService.addEmployee(employeeDto);
        return ResponseEntity.ok("Employee added successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> updateEmployee(@PathVariable int id, @Valid @RequestBody EmployeeRequestDto employeeDto) {
        employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.ok("Employee updated successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}