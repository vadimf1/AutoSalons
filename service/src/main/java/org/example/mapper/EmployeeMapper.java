package org.example.mapper;

import org.example.dto.request.EmployeeRequestDto;
import org.example.dto.response.EmployeeResponseDto;
import org.example.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeResponseDto toDto(Employee employee);
    Employee toEntity(EmployeeRequestDto employeeDto);
    void updateEntityFromDto(EmployeeRequestDto employeeRequestDto, @MappingTarget Employee employee);
}
