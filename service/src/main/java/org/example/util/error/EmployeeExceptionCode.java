package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmployeeExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    EMPLOYEE_NOT_FOUND_BY_ID("Employee not found with ID: ");

    private final String message;
}
