package org.spring.secureapp.auth;

import lombok.RequiredArgsConstructor;
import org.spring.secureapp.UserRepository;
import org.spring.secureapp.config.JwtService;
import org.spring.secureapp.user.Role;
import org.spring.secureapp.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest req) {
        var user = User.builder()
                .firstname(req.firstname())
                .lastname(req.lastname())
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );
        var user = userRepository.findByEmail(req.email()).orElseThrow();
        var token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

}
