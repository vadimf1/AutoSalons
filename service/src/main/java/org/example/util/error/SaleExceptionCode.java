package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SaleExceptionCode {
    ID_FIELD_EXPECTED_NULL("ID field expected null"),
    SALE_NOT_FOUND_BY_ID("Sale not found with ID: ");

    private final String message;
}
