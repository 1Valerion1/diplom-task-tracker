package edu.pet.tasktrackerapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "plan")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Plan {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String developer;
    @Column
    @JsonIgnore
    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private final List<Theme> theme = new ArrayList<>();


}
