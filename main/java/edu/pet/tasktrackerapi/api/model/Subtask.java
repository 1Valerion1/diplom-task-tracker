package edu.pet.tasktrackerapi.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@Table(name = "subtasks")
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Subtask extends AbstractTask {
    // будет списком задач в задаче

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private Task task;
}
