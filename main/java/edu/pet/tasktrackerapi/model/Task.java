package edu.pet.tasktrackerapi.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Task extends AbstractTask implements Serializable {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Column
    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private final List<Subtask> subtask = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return getId() != null && Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
