package edu.pet.tasktrackerapi.api.dto;

import edu.pet.tasktrackerapi.api.model.Enum.Priorities;
import edu.pet.tasktrackerapi.api.model.Subtask;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@Data
        //@RequiredArgsConstructor
@AllArgsConstructor
@Schema(description = "Information about subtask is task")
public class SubtaskDTO implements Serializable  {

    private Long id;
    private Long taskId;
    @NotBlank
    private String title;
    @NotNull
    private String details;
    @Column
    @Enumerated(EnumType.STRING)
    private  Priorities priorities;

    private boolean completed;
}