package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CarExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    ID_FIELD_NOT_NULL("ID field should not be null"),
    CAR_NOT_FOUNT_BY_ID("Car not found with ID: "),
    CAR_NOT_FOUNT_BY_VIN("Car not found with VIN: ");

    private final String message;
}
