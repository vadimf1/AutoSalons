package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DealerCarDatabaseExceptionCode {
    ERROR_ADDING_DEALER_CAR("Error adding dealer car"),
    ERROR_GETTING_DEALER_CAR_PRICE("Error getting dealer car price"),
    ERROR_UPDATING_DEALER_CAR_PRICE("Error updating dealer car price"),
    ERROR_DELETING_DEALER_CAR("Error deleting dealer car"),
    ERROR_MAPPING_RESULTSET_TO_DEALERCARS_OBJECTS("Error mapping ResultSet to DealersCars objects"),
    ERROR_EXPECTED_ONE_RESULT_BUT_FOUND("Expected one result, but found: "),
    ERROR_RETRIEVING_DEALER_CARS_BY_DEALER_ID("Error retrieving dealer cars by dealer ID"),
    ERROR_RETRIEVING_DEALER_CAR_BY_MODEL_AND_YEAR("Error retrieving dealer car by model and year"),
    ERROR_RETRIEVING_ALL_DEALER_CARS_WITH_PRICE_GREATER_THAN("Error retrieving all dealer cars with price greater than specified");

    private final String message;
}
