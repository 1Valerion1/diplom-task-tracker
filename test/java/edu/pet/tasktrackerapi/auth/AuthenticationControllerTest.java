package edu.pet.tasktrackerapi.auth;

import edu.pet.tasktrackerapi.auth.dto.AuthenticationRequest;
import edu.pet.tasktrackerapi.auth.dto.AuthenticationResponse;
import edu.pet.tasktrackerapi.auth.dto.RegisterRequest;
import edu.pet.tasktrackerapi.auth.service.AuthenticationService;
import edu.pet.tasktrackerapi.auth.service.JwtService;
import edu.pet.tasktrackerapi.dao.exception.BadCredentialsException;
import edu.pet.tasktrackerapi.dao.exception.PasswordsNotSameException;
import edu.pet.tasktrackerapi.dao.exception.UserEmailException;
import edu.pet.tasktrackerapi.dao.repository.planner.UserRepository;
import edu.pet.tasktrackerapi.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationControllerTest {

    @AfterEach
    void afterEach() {
        System.out.println("Тест успешно пройден!");
    }

    @Test
    void authoRegisterIfCreate() {
        RegisterRequest registerRequest = new RegisterRequest("string@gmail.com", "string@gmail.com",
                "string@gmail.com", "string@gmail.com");

        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        JwtService jwtService = mock(JwtService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

        //устанавливаем ожидаемое поведение false - на повтор почты и задаем возврат токена
        when(userRepository.existsEmailByEmail(anyString())).thenReturn(false);
        when(jwtService.generateToken(any(User.class))).thenReturn("test_jwt_token");

        AuthenticationService authenticationService =
                new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager);

        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);

        Assertions.assertNotNull(authenticationResponse);
        Assertions.assertEquals("test_jwt_token", authenticationResponse.getToken());
    }

    @Test
    void registerIfUserExists() {
        RegisterRequest registerRequest = new RegisterRequest("string@gmail.com", "password", "password", "username");

        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        JwtService jwtService = mock(JwtService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

        when(userRepository.existsEmailByEmail(anyString())).thenReturn(true);

        AuthenticationService authenticationService =
                new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager);

        Assertions.assertThrows(UserEmailException.class, () -> {
            authenticationService.register(registerRequest);
        });
    }
    @Test
    void registerIfPasswordsNotEqual() {
        RegisterRequest registerRequest = new RegisterRequest("string@gmail.com", "password1", "password2", "username");

        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        JwtService jwtService = mock(JwtService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

        when(userRepository.existsEmailByEmail(anyString())).thenReturn(false);

        AuthenticationService authenticationService =
                new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager);

        Assertions.assertThrows(PasswordsNotSameException.class, () -> {
            authenticationService.register(registerRequest);
        });
    }
    @Test
    void authoAuthenticationIfSuccess() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("string@gmail.com", "password");

        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        JwtService jwtService = mock(JwtService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

        User mockUser = new User();
        mockUser.setEmail("string@gmail.com");
        mockUser.setPassword("encodedPassword");

        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(mockUser));
        when(jwtService.generateToken(any(User.class))).thenReturn("test_jwt_token");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        AuthenticationService authenticationService =
                new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager);

        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

        Assertions.assertNotNull(authenticationResponse);
        Assertions.assertEquals("test_jwt_token", authenticationResponse.getToken());
    }

    @Test
    void authoAuthenticationIfUserNotFound() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("string@gmail.com", "password");

        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        JwtService jwtService = mock(JwtService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

        User mockUser = new User();
        mockUser.setEmail("string@gmail.com");
        mockUser.setPassword("encodedPassword");

        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.empty());

        AuthenticationService authenticationService =
                new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager);

        Assertions.assertThrows(BadCredentialsException.class, () -> {
            authenticationService.authenticate(authenticationRequest);
        });

    }
}
