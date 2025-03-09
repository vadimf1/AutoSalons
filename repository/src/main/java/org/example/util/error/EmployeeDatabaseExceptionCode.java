package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmployeeDatabaseExceptionCode {
    ADD_EMPLOYEE_ERROR("Error adding employee"),
    GET_EMPLOYEE_BY_ID_ERROR("Error getting employee by id"),
    UPDATE_EMPLOYEE_ERROR("Error updating employee"),
    DELETE_EMPLOYEE_ERROR("Error deleting employee"),
    ERROR_MAPPING_RESULTSET_TO_EMPLOYEE_OBJECTS("Error mapping ResultSet to Employee objects"),
    ERROR_EXPECTED_ONE_RESULT_BUT_FOUND("Expected one result, but found: "),
    ERROR_RETRIEVING_EMPLOYEES_BY_POSITION("Error retrieving employees by position"),
    ERROR_RETRIEVING_EMPLOYEES_BY_SALARY("Error retrieving employees with salary greater than specified");

    private final String message;
}
