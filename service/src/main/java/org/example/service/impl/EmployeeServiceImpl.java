package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.EmployeeRequestDto;
import org.example.dto.response.EmployeeResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.EmployeeMapper;
import org.example.model.Employee;
import org.example.repository.AddressRepository;
import org.example.repository.AutoSalonRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.PersonRepository;
import org.example.repository.UserRepository;
import org.example.service.EmployeeService;
import org.example.util.error.AddressExceptionCode;
import org.example.util.error.AutoSalonExceptionCode;
import org.example.util.error.EmployeeExceptionCode;
import org.example.util.error.PersonExceptionCode;
import org.example.util.error.UserExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final AutoSalonRepository autoSalonRepository;
    private final AddressRepository addressRepository;

    public void addEmployee(EmployeeRequestDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);

        addRelationsToEmployee(employee, employeeDto);

        employeeRepository.save(employee);
    }

    private void addRelationsToEmployee(Employee employee, EmployeeRequestDto employeeDto) {
        employee.setPerson(
                personRepository.findById(employeeDto.getPersonId())
                        .orElseThrow(() -> new ServiceException(PersonExceptionCode.PERSON_NOT_FOUNT_BY_ID.getMessage() + employeeDto.getPersonId()))
        );

        employee.setUser(
                userRepository.findById(employeeDto.getUserId())
                        .orElseThrow(() -> new ServiceException(UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + employeeDto.getUserId()))
        );

        employee.setAutoSalon(
                autoSalonRepository.findById(employeeDto.getAutoSalonId())
                        .orElseThrow(() -> new ServiceException(AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + employeeDto.getAutoSalonId()))
        );

        employee.setAddress(
                addressRepository.findById(employeeDto.getAddressId())
                        .orElseThrow(() -> new ServiceException(AddressExceptionCode.ADDRESS_NOT_FOUNT_BY_ID.getMessage() + employeeDto.getAddressId()))
        );
    }

    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    public EmployeeResponseDto getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new ServiceException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND_BY_ID.getMessage() + id));
    }

    public void updateEmployee(int id, EmployeeRequestDto employeeDto) {
        Employee employee = employeeRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND_BY_ID.getMessage() + id));

        employeeMapper.updateEntityFromDto(employeeDto, employee);
        addRelationsToEmployee(employee, employeeDto);

        employeeRepository.save(employee);
    }

    public void deleteEmployeeById(int id) {
        employeeRepository.deleteById(id);
    }
}
