package edu.pet.tasktrackerapi.api.model;

import edu.pet.tasktrackerapi.api.model.Enum.Priorities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor(force = true)
public abstract class AbstractTask {
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
    @Enumerated(EnumType.STRING)
    @Column
    private Priorities priorities;
    @Column(nullable = false)
    private boolean completed;
    @Column(nullable = true)
    private Timestamp completedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTask abstractTask = (AbstractTask) o;
        return Objects.equals(getId(), abstractTask.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}