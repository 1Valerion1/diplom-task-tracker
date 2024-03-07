package edu.pet.tasktrackerapi.auth.service;

import edu.pet.tasktrackerapi.api.model.Enum.Role;
import edu.pet.tasktrackerapi.api.model.User;
import edu.pet.tasktrackerapi.auth.dto.AuthenticationRequest;
import edu.pet.tasktrackerapi.auth.dto.AuthenticationResponse;
import edu.pet.tasktrackerapi.auth.dto.RegisterRequest;
import edu.pet.tasktrackerapi.exception.BadCredentialsException;
import edu.pet.tasktrackerapi.exception.PasswordsNotSameException;
import edu.pet.tasktrackerapi.repository.planner.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if (isPasswordsNotEqual(registerRequest)) {
            throw new PasswordsNotSameException();
        }

        var user = User.
                builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword())
        );

        var user = userRepository
                .findByEmail(authenticationRequest.getEmail())
                .get();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public boolean userExists(String username){
        return userRepository.existsEmailByEmail(username);
    }

    public boolean isCredentialsValid(AuthenticationRequest authenticationRequest) {

        String requestEmail = authenticationRequest.getEmail();
        String reqPassword = authenticationRequest.getPassword();

        // Тут в чем-то беда с аутефикацией
        String dbPassword = userRepository.findByEmail(requestEmail)
                .orElseThrow(BadCredentialsException::new)
                .getPassword();

        return passwordEncoder.matches(reqPassword, dbPassword);
    }


    private static boolean isPasswordsNotEqual(RegisterRequest registrationUserDto) {
        return !registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword());
    }

}
