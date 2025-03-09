package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserDatabaseExceptionCode {
    ERROR_ADDING_USER("Error adding user"),
    ERROR_RETRIEVING_USER_BY_ID("Error retrieving user by ID"),
    ERROR_UPDATING_USER("Error updating user"),
    ERROR_DELETING_USER("Error deleting user"),
    ERROR_MAPPING_RESULTSET_TO_USER_OBJECTS("Error mapping ResultSet to User objects"),
    ERROR_EXPECTED_ONE_RESULT_BUT_FOUND("Expected one result, but found: ");

    private final String message;
}
