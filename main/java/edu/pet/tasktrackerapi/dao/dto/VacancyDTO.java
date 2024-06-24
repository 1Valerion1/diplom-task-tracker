package edu.pet.tasktrackerapi.dao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class VacancyDTO {
    @JsonProperty("profession")
    @NotBlank
    private String profession;
    @JsonProperty("found")
    @NotEmpty
    private int numberVacancies;
    @JsonProperty("alternate_url")
    @NotEmpty
    private String alternateUrl;

}
