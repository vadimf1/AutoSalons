package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CarDatabaseExceptionCode {
    ADD_CAR_ERROR("Error adding car with related entities"),
    FETCH_CAR_BY_ID_ERROR("Error fetching car by ID"),
    FETCH_CARS_BY_YEAR_ERROR("Error fetching cars by year"),
    FETCH_CAR_BY_VIN_ERROR("Error fetching car by VIN"),
    FETCH_CARS_BY_PARAMETER_ERROR("Error fetching cars by parameter"),
    FETCH_CAR_PRICE_ERROR("Error fetching car price by dealer"),
    UPDATE_CAR_ERROR("Error updating car information"),
    DELETE_CAR_ERROR("Error deleting car"),
    ERROR_MAPPING_RESULTSET_TO_CAR_OBJECTS("Error mapping ResultSet to Car objects"),
    ERROR_EXPECTED_ONE_RESULT_BUT_FOUND("Expected one result, but found: ");

    private final String message;
}
