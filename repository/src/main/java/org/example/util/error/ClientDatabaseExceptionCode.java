package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClientDatabaseExceptionCode {
    ADD_CLIENT_ERROR("Error adding client"),
    FETCH_CLIENTS_ERROR("Error fetching clients"),
    FETCH_CLIENT_BY_ID_ERROR("Error retrieving client by ID"),
    FETCH_CLIENT_BY_PASSPORT_NUMBER_ERROR("Error retrieving client by passport number"),
    UPDATE_CLIENT_ERROR("Error updating client"),
    DELETE_CLIENT_ERROR("Error deleting client"),
    ERROR_MAPPING_RESULTSET_TO_CLIENT_OBJECTS("Error mapping ResultSet to Client objects"),
    ERROR_EXPECTED_ONE_RESULT_BUT_FOUND("Expected one result, but found: "),
    FETCH_CLIENTS_BY_BIRTH_DATE_ERROR("Error fetching clients by birth date"),
    FETCH_CLIENTS_BY_USER_ID_ERROR("Error fetching clients by user ID");


    private final String message;
}
