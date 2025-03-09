package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClientExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    CLIENT_NOT_FOUND_BY_ID("Client not found with ID: "),
    CLIENT_NOT_FOUNT_BY_PASSPORT_NUMBER("Client not found with passport number: ");

    private final String message;
}
