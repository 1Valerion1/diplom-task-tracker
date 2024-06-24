package edu.pet.tasktrackerapi.dao.dto;

import edu.pet.tasktrackerapi.model.Enum.Priorities;
import edu.pet.tasktrackerapi.model.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A DTO for the {@link Task} entity
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "Информация о задаче(ах) пользователя")
public class TaskResponse implements Serializable{

    private Long id;
    @NotBlank
    private String title;
    @NotNull
    private String details;
    @NotNull
    @Enumerated(EnumType.STRING)
    private  Priorities priorities;

    private boolean completed;
    private Timestamp completedAt;

}