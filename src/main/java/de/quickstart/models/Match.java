package de.quickstart.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "matches")
@Getter
@Setter
public class Match {
    @Id
    private Long id; // Match ID

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "competition_edition_id")
    private CompetitionEdition competitionEdition;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    private String name; // optional human-friendly name

    @OneToMany(mappedBy = "match")
    private Set<MatchPerformance> performances = new HashSet<>();
}
