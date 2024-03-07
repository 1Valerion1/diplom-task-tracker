package edu.pet.tasktrackerapi.ParserHH.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyDTO {
    @JsonProperty("profession")
    private String profession;
    @JsonProperty("found")
    private int numberVacancies;
    @JsonProperty("alternate_url")
    private String alternateUrl;


    public VacancyDTO() {
    }

    public VacancyDTO(String profession, int numberVacancies, String alternateUrl) {
        this.profession = profession;
        this.numberVacancies = numberVacancies;
        this.alternateUrl = alternateUrl;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getNumberVacancies() {
        return numberVacancies;
    }

    public void setNumberVacancies(int numberVacancies) {
        this.numberVacancies = numberVacancies;
    }

    public String getAlternateUrl() {
        return alternateUrl;
    }

    public void setAlternateUrl(String alternateUrl) {
        this.alternateUrl = alternateUrl;
    }

}
