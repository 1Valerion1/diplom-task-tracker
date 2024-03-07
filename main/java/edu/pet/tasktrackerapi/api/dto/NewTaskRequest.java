package edu.pet.tasktrackerapi.api.dto;

import edu.pet.tasktrackerapi.api.model.Enum.Priorities;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Schema(description = "Information about new Task")
public class NewTaskRequest implements Serializable {
    @NotBlank
    private String title;
    @NotNull
    private String details;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Priorities priorities;

}
