package edu.pet.tasktrackerapi.api.model;

import edu.pet.tasktrackerapi.api.model.Enum.Priorities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Task  implements Serializable {

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
