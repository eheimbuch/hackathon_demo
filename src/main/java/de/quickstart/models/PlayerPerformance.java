package de.quickstart.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "player_match_metrics")
@Setter
@Getter
public class PlayerPerformance {

    @Id
    private Long playerId;

    private String playerName;
    private String shortName;
    private Double totalMinutesPerGame;
    private Double totalMinutes;
    private Double totalDistance;
    private Double totalMetersPerMin;
    private Double totalRunningDistance;
    private Double totalHsrDistance;
    private Integer totalHsrCount;
    private Double totalSprintDistance;
    private Integer totalSprintCount;
    private Double totalHiDistance;
    private Integer totalHiCount;
    private Double totalPsv_99;
    private Integer totalMediumAccCount;
    private Integer totalHighAccCount;
    private Integer totalMediumDecCount;
    private Integer totalHighDecCount;
    private Integer totalExplAccToHsrCount;
    private Double totalTimeToHsr;
    private Double totalTimeToHsrPostCod;
    private Integer totalExplAccToSprintCount;
    private Double totalTimeToSprint;
    private Double totalTimeToSprintPostCod;
    private Integer totalCodCount;
    private Double totalTimeTo_505_90;
    private Double totalTimeTo_505_180;
}
