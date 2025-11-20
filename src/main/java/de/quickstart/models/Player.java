package de.quickstart.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "players")
@Getter
@Setter
public class Player {
    @Id
    private Long id; // Player ID from CSV

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "name")
    private String fullName;

    private LocalDate birthdate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "player")
    private Set<MatchPerformance> performances = new HashSet<>();
}
