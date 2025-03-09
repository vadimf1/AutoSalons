package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TestDriveExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    TEST_DRIVE_NOT_FOUNT_BY_ID("TestDrive not found with ID: "),
    CLIENT_ID_REQUIRED("Client ID must not be null"),
    CAR_ID_REQUIRED("Car ID is required for the test drive"),
    AUTO_SALON_ID_REQUIRED("Auto salon ID is required for the test drive");

    private final String message;
}
