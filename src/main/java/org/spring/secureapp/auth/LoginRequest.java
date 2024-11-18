package org.spring.secureapp.auth;

public record LoginRequest(
        String email,
        String password
) {
}
