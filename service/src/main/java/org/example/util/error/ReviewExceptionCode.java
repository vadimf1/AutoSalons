package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReviewExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    REVIEW_NOT_FOUND_BY_ID("Review not found with ID: "),
    INVALID_REVIEW_ASSOCIATION("Exactly one of Car, Dealer, or AutoSalon must be provided.");


    private final String message;
}
