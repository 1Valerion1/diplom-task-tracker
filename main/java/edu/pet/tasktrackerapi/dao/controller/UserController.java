package edu.pet.tasktrackerapi.dao.controller;

import edu.pet.tasktrackerapi.dao.dto.UserDto;
import edu.pet.tasktrackerapi.model.User;
import edu.pet.tasktrackerapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User endpoints", description = "Methods for working with current authorized user")
public class UserController {
    private final UserService userService;

    @Operation(description = "Getting information about current authorized User",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Getting user id, username and task-list",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "id": 1,
                                                                "username": "user",
                                                                "tasks": [
                                                                    {
                                                                        "id": "066a2e14-8b0b-45ac-8336-2e57c8da2846",
                                                                        "title": "example",
                                                                        "details": "example",
                                                                        "priorities": "HIGH",
                                                                        "completed": false
                                                                    }
                                                                ]
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Responds with an Forbidden error if user don't authorized bby token",
                            content = @Content(
                    )
                    )
            })

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/getUser", produces = "application/json")
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal User user){

       return ResponseEntity.ok(userService.getUserInfo(user));
    }

    @GetMapping
    public String showUser(Model model) {
        return "user";
    }

}
