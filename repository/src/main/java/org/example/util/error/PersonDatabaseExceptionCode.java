package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PersonDatabaseExceptionCode {
    ADD_ERROR("Error adding person"),
    GET_BY_ID_ERROR("Error getting person by id"),
    GET_ALL_ERROR("Error retrieving all persons"),
    UPDATE_ERROR("Error updating person"),
    DELETE_ERROR("Error deleting person"),
    ERROR_MAPPING_RESULTSET_TO_PERSON_OBJECTS("Error mapping ResultSet to Person objects"),
    ERROR_EXPECTED_ONE_RESULT_BUT_FOUND("Expected one result, but found: "),
    ERROR_RETRIEVING_PERSONS_BY_FIRST_NAME("Error retrieving persons by first name"),
    ERROR_CHECKING_PERSON_EXISTENCE("Error checking if person exists by first name"),
    ERROR_RETRIEVING_PERSONS_WITH_CONTACTS_COUNT("Error retrieving persons with specified contacts count");

    private final String message;
}
