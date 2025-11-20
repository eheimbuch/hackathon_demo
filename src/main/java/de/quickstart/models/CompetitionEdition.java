package de.quickstart.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "competition_editions")
@Getter
@Setter
public class CompetitionEdition {
    @Id
    private Long id; // Competition Edition ID

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    private String name;
}
