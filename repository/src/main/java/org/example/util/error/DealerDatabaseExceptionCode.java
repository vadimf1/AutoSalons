package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DealerDatabaseExceptionCode {
    ERROR_ADDING_DEALER("Error adding dealer"),
    ERROR_GETTING_DEALER_BY_ID("Error getting dealer by id"),
    ERROR_GETTING_DEALER_BY_NAME("Error getting dealer by name"),
    ERROR_GETTING_DEALER_CARS("Error getting dealer cars"),
    ERROR_UPDATING_DEALER("Error updating dealer"),
    ERROR_DELETING_DEALER("Error deleting dealer"),
    ERROR_MAPPING_RESULTSET_TO_DEALER_OBJECTS("Error mapping ResultSet to Dealer objects"),
    ERROR_EXPECTED_ONE_RESULT_BUT_FOUND("Expected one result, but found: "),
    ERROR_RETRIEVING_DEALERS_BY_ADDRESS_ID("Error retrieving dealers by address ID");


    private final String message;
}
