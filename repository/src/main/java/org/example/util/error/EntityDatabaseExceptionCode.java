package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EntityDatabaseExceptionCode {
    ENTITY_NOT_FOUND_ERROR("Entity not found with id: "),
    ERROR_ADDING_ENTITY("Error occurred while adding entity"),
    ERROR_UPDATING_ENTITY("Error occurred while updating entity"),
    ERROR_DELETING_ENTITY("Error occurred while deleting entity"),
    ERROR_RETRIEVING_ALL_ENTITIES("Error occurred while retrieving all entities"),
    ERROR_RETRIEVING_ENTITY_BY_ID("Error occurred while retrieving entity by ID");

    private final String message;
}
