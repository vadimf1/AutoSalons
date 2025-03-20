package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    USER_NOT_FOUND_BY_ID("User not found with ID: "),
    USER_NOT_FOUND_BY_USERNAME("User not found with username: "),
    USER_ALREADY_EXISTS("User already exists");

    private final String message;
}
