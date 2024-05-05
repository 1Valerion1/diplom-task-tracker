package edu.pet.tasktrackerapi.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "vacancy")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String profession;
    @Column
    private int numberVacancies;
    @Column
    private String Url;



}
