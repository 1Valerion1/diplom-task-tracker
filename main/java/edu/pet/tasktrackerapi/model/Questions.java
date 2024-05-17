package edu.pet.tasktrackerapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Questions {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String titleQuestions;
    @NotBlank
    @Column(nullable = false)
    private String nameThemes;
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String answer;
    @Column(nullable = false)
    private String links;
    
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Theme theme;
}
