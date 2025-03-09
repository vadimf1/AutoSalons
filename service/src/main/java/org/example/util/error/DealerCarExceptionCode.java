package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DealerCarExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    DEALER_CAR_NOT_FOUND_BY_ID("Dealer car not found with ID: ");

    private final String message;
}
