package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReviewDatabaseExceptionCode {
    ERROR_ADD_REVIEW("Error adding review"),
    ERROR_ADD_REVIEW_TO_AUTOSALON("Error adding review to autosalon"),
    ERROR_ADD_REVIEW_TO_DEALER("Error adding review to dealer"),
    ERROR_ADD_REVIEW_TO_CAR("Error adding review to car"),
    ERROR_FETCH_REVIEWS_BY_RATING("Error fetching reviews by rating"),
    ERROR_FETCH_REVIEWS_BY_DATE("Error fetching reviews by date"),
    ERROR_FETCH_REVIEWS_BY_CAR_ID("Error fetching reviews by car ID"),
    ERROR_FETCH_REVIEWS_BY_DEALER_ID("Error fetching reviews by dealer ID"),
    ERROR_FETCH_REVIEWS_BY_AUTOSALON_ID("Error fetching reviews by autosalon ID"),
    ERROR_UPDATE_REVIEW("Error updating review"),
    ERROR_DELETE_REVIEW("Error deleting review"),
    ERROR_MAPPING_RESULTSET_TO_REVIEW_OBJECTS("Error mapping ResultSet to Review objects"),
    ERROR_EXPECTED_ONE_RESULT_BUT_FOUND("Expected one result, but found: ");

    private final String message;
}
