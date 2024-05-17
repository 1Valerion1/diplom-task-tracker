package edu.pet.tasktrackerapi.dao.dto;

import edu.pet.tasktrackerapi.model.Enum.Priorities;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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