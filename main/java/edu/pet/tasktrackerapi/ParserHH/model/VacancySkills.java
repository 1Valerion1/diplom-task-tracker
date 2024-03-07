package edu.pet.tasktrackerapi.ParserHH.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @ManyToOne(fetch = FetchType.EAGER)
    private Vacancy vacancy;


}
