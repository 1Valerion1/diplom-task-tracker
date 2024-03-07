package edu.pet.tasktrackerapi.api.model;

import edu.pet.tasktrackerapi.api.model.Enum.Priorities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
@Entity
@Data
@Table(name = "subtasks")
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Subtask {
    // будет списком задач в задаче
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String title;
    @NotNull
    @Column(nullable = false)
    private String details;
    @Column
    @Enumerated(EnumType.STRING)
    private final Priorities priorities;
    @Column(nullable = false)
    private boolean completed;
    @Column(nullable = true)
    private Timestamp completedAt;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private Task task;
}
