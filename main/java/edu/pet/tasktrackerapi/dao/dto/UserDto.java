package edu.pet.tasktrackerapi.dao.dto;

import edu.pet.tasktrackerapi.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link User} entity
 */
@Data
@AllArgsConstructor
@Schema(description = "Information about User")
public class UserDto implements Serializable {
    private Long id;
    private String email;
    private String username;
    private List<TaskResponse> tasks;
}