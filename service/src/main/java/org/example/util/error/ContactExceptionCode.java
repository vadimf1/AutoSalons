package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContactExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    CONTACT_NOT_FOUND_BY_ID("Contact not found with ID: ");

    private final String message;
}
