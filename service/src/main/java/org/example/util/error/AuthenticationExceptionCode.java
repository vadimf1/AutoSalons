package org.example.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthenticationExceptionCode {
    INVALID_USERNAME_OR_PASSWORD("Invalid username or password");

    private final String message;
}
