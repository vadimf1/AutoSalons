package org.example.service;

import org.example.dto.request.EmployeeRequestDto;
import org.example.dto.response.EmployeeResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.EmployeeMapper;
import org.example.model.Address;
import org.example.model.AutoSalon;
import org.example.model.Employee;
import org.example.model.Person;
import org.example.model.User;
import org.example.repository.*;
import org.example.service.impl.EmployeeServiceImpl;
import org.example.util.error.EmployeeExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AutoSalonRepository autoSalonRepository;
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private Person person;
    private User user;
    private AutoSalon autoSalon;
    private Address address;
    private EmployeeRequestDto employeeRequestDto;
    private EmployeeResponseDto employeeResponseDto;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employeeRequestDto = EmployeeRequestDto.builder()
                .personId(1)
                .userId(2)
                .autoSalonId(3)
                .addressId(4)
                .position("Manager")
                .salary(5000)
                .build();
        employeeResponseDto = EmployeeResponseDto.builder()
                .id(1)
                .position("Manager")
                .salary(5000)
                .build();

        person = new Person();
        user = new User();
        autoSalon = new AutoSalon();
        address = new Address();
    }

    @Test
    void addEmployee_ShouldSaveEmployee() {
        when(personRepository.findById(employeeRequestDto.getPersonId())).thenReturn(Optional.of(person));
        when(userRepository.findById(employeeRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(autoSalonRepository.findById(employeeRequestDto.getAutoSalonId())).thenReturn(Optional.of(autoSalon));
        when(addressRepository.findById(employeeRequestDto.getAddressId())).thenReturn(Optional.of(address));
        when(employeeMapper.toEntity(employeeRequestDto)).thenReturn(employee);

        employeeService.addEmployee(employeeRequestDto);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponseDto);
        List<EmployeeResponseDto> result = employeeService.getAllEmployees();
        assertEquals(1, result.size());
        assertEquals("Manager", result.get(0).getPosition());
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponseDto);
        EmployeeResponseDto result = employeeService.getEmployeeById(1);
        assertNotNull(result);
        assertEquals("Manager", result.getPosition());
    }

    @Test
    void getEmployeeById_ShouldThrowException_WhenNotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
        ServiceException exception = assertThrows(ServiceException.class, () -> employeeService.getEmployeeById(1));
        assertEquals(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND_BY_ID.getMessage() + 1, exception.getMessage());
    }

    @Test
    void updateEmployee_ShouldUpdateEmployee() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(personRepository.findById(employeeRequestDto.getPersonId())).thenReturn(Optional.of(person));
        when(userRepository.findById(employeeRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(autoSalonRepository.findById(employeeRequestDto.getAutoSalonId())).thenReturn(Optional.of(autoSalon));
        when(addressRepository.findById(employeeRequestDto.getAddressId())).thenReturn(Optional.of(address));
        doNothing().when(employeeMapper).updateEntityFromDto(employeeRequestDto, employee);

        employeeService.updateEmployee(1, employeeRequestDto);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void deleteEmployeeById_ShouldDeleteEmployee() {
        doNothing().when(employeeRepository).deleteById(1);
        employeeService.deleteEmployeeById(1);
        verify(employeeRepository, times(1)).deleteById(1);
    }
}
