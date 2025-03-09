package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AutoSalonExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    AUTO_SALON_NOT_FOUNT_BY_ID("Auto salon not found with ID: ");

    private final String message;
}
