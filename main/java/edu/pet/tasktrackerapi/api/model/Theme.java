package edu.pet.tasktrackerapi.api.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


@Entity
@Data
@Builder
@Table(name = "theme")
public class Theme implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String details;
    @Column
    private String Links;

    @Column
    @OneToMany(mappedBy = "theme", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Questions> questionsList;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private Plan plan;


}
