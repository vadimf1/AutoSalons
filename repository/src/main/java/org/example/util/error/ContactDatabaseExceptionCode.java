package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContactDatabaseExceptionCode {
    ADD_CONTACT_ERROR("Error adding contact to the database"),
    UPDATE_CONTACT_ERROR("Error updating contact in the database"),
    DELETE_CONTACT_ERROR("Error deleting contact from the database"),
    FETCH_CONTACT_ERROR("Error fetching contact from the database"),
    ADD_CONTACT_TO_PERSON_ERROR("Error adding contact to person"),
    ADD_CONTACT_TO_DEALER_ERROR("Error adding contact to dealer"),
    ADD_CONTACT_TO_AUTOSALON_ERROR("Error adding contact to autosalon"),
    ERROR_MAPPING_RESULTSET_TO_CONTACT_OBJECTS("Error mapping ResultSet to Contact objects"),
    ERROR_EXPECTED_ONE_RESULT_BUT_FOUND("Expected one result, but found: ");

    private final String message;
}
