package edu.pet.tasktrackerapi.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.pet.tasktrackerapi.model.User;
import edu.pet.tasktrackerapi.auth.dto.AuthenticationRequest;
import edu.pet.tasktrackerapi.auth.dto.AuthenticationResponse;
import edu.pet.tasktrackerapi.auth.dto.RegisterRequest;
import edu.pet.tasktrackerapi.auth.service.AuthenticationService;
import edu.pet.tasktrackerapi.dao.exception.PasswordsNotSameException;
import edu.pet.tasktrackerapi.rabbitmq.producer.RabbitMessageSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "JWT-security", description = "Методы регистрации и аутентификации")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RabbitMessageSender rabbitMessageSender;
    @Operation(description = "Регистрирует нового пользователя",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Getting JWT-token after successful registration",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlIiwiaWF0IjoxNjgzMDc2MzUwLCJleHAiOjE2ODMwNzc3OTB9.gg4XpZ7HMqSbCjV4eBw7Wluoe2D23goB68D9gxG-ntM"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Responds with an Conflict error if username is taken",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "message": "This username is already taken!"
                                                    }
                                                    """

                                    )
                            }
                    )
            )
        })
    @PostMapping(value = "/register", produces="application/json")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) throws JsonProcessingException {

            AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);

            rabbitMessageSender.sendWelcomeEmail(registerRequest.getEmail());

            return ResponseEntity.ok(authenticationResponse);

    }

    @Operation(description = "Аутентификация пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Getting JWT-token after successful authentication",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlIiwiaWF0IjoxNjgzMDc2MzUwLCJleHAiOjE2ODMwNzc3OTB9.gg4XpZ7HMqSbCjV4eBw7Wluoe2D23goB68D9gxG-ntM"
                                                            }"""
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Responds with an Unauthorized error if username/password is invalid",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "message": "Bad credentials!"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    )
            })
    @PostMapping(value = "/authenticate", produces="application/json")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));

    }

    @PostMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal User currentUser,
                                           @RequestParam String newUsername, @RequestParam String newEmail) {
        // Обновление данных пользователя
        return ResponseEntity.ok("Profile updated successfully");
    }
    @Operation(hidden = true)
    @GetMapping
    public String showAutorize(Model model) {
        return "autorize";
    }
}


