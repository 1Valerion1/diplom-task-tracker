package edu.pet.tasktrackerapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@Table(name = "subtasks")
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Subtask extends AbstractTask implements Serializable {
    // будет списком задач в задаче

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Task task;
}
