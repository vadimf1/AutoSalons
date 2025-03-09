package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DealerExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    DEALER_NOT_FOUNT_BY_ID("Dealer not found with ID: "),
    DEALER_NOT_FOUND_BY_NAME("Dealer not found with name: ");

    private final String message;
}
