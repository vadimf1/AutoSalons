package org.example.mapper;

import org.example.dto.EmployeeDto;
import org.example.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto toDto(Employee employee);
    Employee toEntity(EmployeeDto employeeDto);
}
