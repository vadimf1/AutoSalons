package org.example.controller;

import org.example.dto.request.EmployeeRequestDto;
import org.example.dto.response.EmployeeResponseDto;
import org.example.exception.ServiceException;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeRequestDto employeeRequestDto;
    private EmployeeResponseDto employeeResponseDto;
    private List<EmployeeResponseDto> employees;

    @BeforeEach
    void setUp() {
        employeeRequestDto = EmployeeRequestDto.builder()
                .personId(1)
                .userId(1)
                .autoSalonId(1)
                .addressId(1)
                .position("Manager")
                .salary(50000.0)
                .build();

        employeeResponseDto = EmployeeResponseDto.builder()
                .build();

        employees = Arrays.asList(employeeResponseDto);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<EmployeeResponseDto>> response = employeeController.getAllEmployees();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(employees, response.getBody());
        verify(employeeService).getAllEmployees();
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() {
        when(employeeService.getEmployeeById(anyInt())).thenReturn(employeeResponseDto);

        ResponseEntity<EmployeeResponseDto> response = employeeController.getEmployeeById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(employeeResponseDto, response.getBody());
        verify(employeeService).getEmployeeById(1);
    }

    @Test
    void createEmployee_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = employeeController.addEmployee(employeeRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Employee added successfully", response.getBody());
        verify(employeeService).addEmployee(employeeRequestDto);
    }

    @Test
    void updateEmployee_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = employeeController.updateEmployee(1, employeeRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Employee updated successfully", response.getBody());
        verify(employeeService).updateEmployee(1, employeeRequestDto);
    }

    @Test
    void deleteEmployee_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = employeeController.deleteEmployee(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Employee deleted successfully", response.getBody());
        verify(employeeService).deleteEmployeeById(1);
    }

    @Test
    void getAllEmployees_ShouldHandleServiceException() {
        when(employeeService.getAllEmployees()).thenThrow(new ServiceException("Failed to get employees"));

        assertThrows(ServiceException.class, () -> employeeController.getAllEmployees());
        verify(employeeService).getAllEmployees();
    }

    @Test
    void getEmployeeById_ShouldHandleServiceException() {
        when(employeeService.getEmployeeById(anyInt())).thenThrow(new ServiceException("Employee not found"));

        assertThrows(ServiceException.class, () -> employeeController.getEmployeeById(1));
        verify(employeeService).getEmployeeById(1);
    }

    @Test
    void createEmployee_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to create employee")).when(employeeService).addEmployee(any(EmployeeRequestDto.class));

        assertThrows(ServiceException.class, () -> employeeController.addEmployee(employeeRequestDto));
        verify(employeeService).addEmployee(employeeRequestDto);
    }

    @Test
    void updateEmployee_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update employee")).when(employeeService).updateEmployee(anyInt(), any(EmployeeRequestDto.class));

        assertThrows(ServiceException.class, () -> employeeController.updateEmployee(1, employeeRequestDto));
        verify(employeeService).updateEmployee(1, employeeRequestDto);
    }

    @Test
    void deleteEmployee_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to delete employee")).when(employeeService).deleteEmployeeById(anyInt());

        assertThrows(ServiceException.class, () -> employeeController.deleteEmployee(1));
        verify(employeeService).deleteEmployeeById(1);
    }
} 