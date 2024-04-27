package edu.pet.tasktrackerapi.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plans")
@Data
public class Plan {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String Developer;
    @Column
    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private final List<Theme> theme = new ArrayList<>();
}
