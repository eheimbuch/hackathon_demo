package de.quickstart.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "match_performances")
@Getter
@Setter
public class MatchPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team; // Team des Spielers in diesem Match

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    private CompetitionEdition competitionEdition;

    // Basisfelder
    private Integer minutes;

    @Column(name = "physical_check_passed")
    private Boolean physicalCheckPassed;

    private Double distance; // Gesamtstrecke (m)

    @Column(name = "m_per_min")
    private Double metersPerMinute;

    @Column(name = "running_distance")
    private Double runningDistance;

    @Column(name = "hsr_distance")
    private Double hsrDistance; // High Speed Running distance

    @Column(name = "hsr_count")
    private Integer hsrCount;

    @Column(name = "sprint_distance")
    private Double sprintDistance;

    @Column(name = "sprint_count")
    private Integer sprintCount;

    @Column(name = "hi_distance")
    private Double hiDistance; // High intensity distance

    @Column(name = "hi_count")
    private Integer hiCount;

    @Column(name = "psv_99")
    private Double psv99;

    @Column(name = "medium_acc_count")
    private Integer mediumAccelerationCount;

    @Column(name = "high_acc_count")
    private Integer highAccelerationCount;

    @Column(name = "medium_dec_count")
    private Integer mediumDecelerationCount;

    @Column(name = "high_dec_count")
    private Integer highDecelerationCount;

    @Column(name = "expl_acc_to_hsr_count")
    private Integer explosiveAccToHsrCount;

    @Column(name = "time_to_hsr")
    private Double timeToHsr; // Sekunden

    @Column(name = "time_to_hsr_post_cod")
    private Double timeToHsrPostCod; // Sekunden after change of direction

    @Column(name = "expl_acc_to_sprint_count")
    private Integer explosiveAccToSprintCount;

    @Column(name = "time_to_sprint")
    private Double timeToSprint; // Sekunden

    @Column(name = "time_to_sprint_post_cod")
    private Double timeToSprintPostCod; // Sekunden

    @Column(name = "change_of_direction_count")
    private Integer changeOfDirectionCount;

    @Column(name = "time_to_505_around_90")
    private Double timeTo505Around90;

    @Column(name = "time_to_505_around_180")
    private Double timeTo505Around180;
}
