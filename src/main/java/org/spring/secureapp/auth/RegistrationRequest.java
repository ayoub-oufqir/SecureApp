package org.spring.secureapp.auth;

public record RegistrationRequest(
        String firstname,
        String lastname,
        String email,
        String password
) {}
