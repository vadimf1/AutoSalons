package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SaleDatabaseExceptionCode {
    ERROR_ADD_SALE("Error adding sale"),
    ERROR_GET_SALES_BY_PERIOD("Error getting sales by period"),
    ERROR_GET_SALES_BY_CLIENT("Error getting sales by client"),
    ERROR_UPDATE_SALE("Error updating sale"),
    ERROR_DELETE_SALE("Error deleting sale"),
    ERROR_MAPPING_RESULTSET_TO_SALE_OBJECTS("Error mapping ResultSet to Sale objects"),
    ERROR_EXPECTED_ONE_RESULT_BUT_FOUND("Expected one result, but found: ");

    private final String message;
}
