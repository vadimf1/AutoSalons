package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AddressExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    ADDRESS_NOT_FOUNT_BY_ID("Address not found with ID: ");

    private final String message;
}
