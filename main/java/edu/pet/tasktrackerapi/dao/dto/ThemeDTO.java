package edu.pet.tasktrackerapi.dao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@Schema(description = "Information about Theme")
public class ThemeDTO {
    @NotNull
    private String title;
    @NotNull
    private String details;
    @NotNull
    private String links;
}
