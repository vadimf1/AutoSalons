package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TestDriveDatabaseExceptionCode {
    ERROR_ADDING_TEST_DRIVE("Error adding test drive"),
    ERROR_UPDATING_TEST_DRIVE("Error updating test drive"),
    ERROR_RETRIEVING_TEST_DRIVES_BY_CLIENT_ID("Error retrieving test drives by client ID"),
    ERROR_RETRIEVING_TEST_DRIVES_BY_DATE("Error retrieving test drives by date"),
    ERROR_DELETING_TEST_DRIVE("Error deleting test drive"),
    ERROR_MAPPING_RESULTSET_TO_TESTDRIVE_OBJECTS("Error mapping ResultSet to TestDrive objects"),
    ERROR_EXPECTED_ONE_RESULT_BUT_FOUND("Expected one result, but found: ");

    private final String message;
}
