package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AddressDatabaseExceptionCode {
    ERROR_ADDING_ADDRESS("Error adding address"),
    ERROR_RETRIEVING_ADDRESS_BY_ID("Error retrieving address by ID: "),
    ERROR_RETRIEVING_ALL_ADDRESSES("Error retrieving all addresses"),
    ERROR_RETRIEVING_ADDRESSES_BY_CITY("Error retrieving addresses by city: "),
    ERROR_RETRIEVING_ADDRESS_BY_POSTAL_CODE("Error retrieving addresses by postal code: "),
    ERROR_RETRIEVING_ADDRESS_BY_COUNTRY_AND_CITY("Error retrieving addresses by country and city: "),
    ERROR_RETRIEVING_ALL_UNIQUE_ADDRESSES("Error retrieving all unique countries"),
    ERROR_UPDATING_ADDRESS("Error updating address with ID: "),
    ERROR_DELETING_ADDRESS("Error deleting address with ID: ");

    private final String message;
}

