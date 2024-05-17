package edu.pet.tasktrackerapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "vacancy_skills")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancySkills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String skillName;
    @Column
    private int skillCount;
    @ToString.Exclude
    @JsonSerialize(as = Vacancy.class)
    @ManyToOne(fetch = FetchType.EAGER)
    private Vacancy vacancy;


}
