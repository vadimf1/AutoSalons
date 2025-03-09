package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PersonExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    PERSON_NOT_FOUNT_BY_ID("Person not found with ID: ");

    private final String message;
}
