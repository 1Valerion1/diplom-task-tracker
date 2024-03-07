package edu.pet.tasktrackerapi.api.dto;

import edu.pet.tasktrackerapi.api.model.Enum.Priorities;
import edu.pet.tasktrackerapi.api.model.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link Task} entity
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(description = "Information about Task")
public class TaskDto implements Serializable{

    private Long id;
    @NotBlank
    private String title;
    @NotNull
    private String details;

    @Enumerated(EnumType.STRING)
    private final Priorities priorities;


    private boolean completed;
}