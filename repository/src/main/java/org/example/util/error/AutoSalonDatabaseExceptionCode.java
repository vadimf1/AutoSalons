package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AutoSalonDatabaseExceptionCode {
    ERROR_ADDING_AUTOSALON("Error adding autosalon"),
    ERROR_FETCHING_AUTOSALON_BY_ID("Error fetching autosalon by ID"),
    ERROR_FETCHING_ALL_AUTOSALONS("Error fetching all autosalons"),
    ERROR_FETCHING_AUTOSALON_BY_ADDRESS_ID("Error fetching autosalon by address ID"),
    ERROR_UPDATING_AUTOSALON("Error updating autosalon"),
    ERROR_DELETING_AUTOSALON("Error deleting autosalon"),
    ERROR_MAPPING_RESULTSET_TO_AUTOSALON_OBJECTS("Error mapping ResultSet to Autosalon objects"),
    ERROR_EXPECTED_ONE_RESULT_BUT_FOUND("Expected one result, but found: ");

    private final String message;
}
