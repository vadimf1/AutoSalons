package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.EmployeeDto;
import org.example.exception.ServiceException;
import org.example.mapper.EmployeeMapper;
import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService;
import org.example.util.error.EmployeeExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public void addEmployee(EmployeeDto employeeDto) {
        if (employeeDto.getId() != null) {
            throw new ServiceException(EmployeeExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }
        employeeRepository.save(employeeMapper.toEntity(employeeDto));
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    public EmployeeDto getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new ServiceException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND_BY_ID.getMessage() + id));
    }

    public void updateEmployee(EmployeeDto employeeDto) {
        employeeRepository.save(employeeMapper.toEntity(employeeDto));
    }

    public void deleteEmployeeById(int id) {
        employeeRepository.deleteById(id);
    }
}
