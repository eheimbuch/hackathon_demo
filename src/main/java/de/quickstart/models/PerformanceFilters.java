package de.quickstart.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class PerformanceFilters {

    @Column(name = "min_distance")
    private Double minDistance;

    @Column(name = "max_distance")
    private Double maxDistance;

    @Column(name = "distance_weight", nullable = false)
    private Double distanceWeight = 1.0;

    @Column(name = "min_m_per_min")
    private Double minMPerMin;

    @Column(name = "max_m_per_min")
    private Double maxMPerMin;

    @Column(name = "m_per_min_weight", nullable = false)
    private Double mPerMinWeight = 1.0;

    @Column(name = "min_running_distance")
    private Double minRunningDistance;

    @Column(name = "max_running_distance")
    private Double maxRunningDistance;

    @Column(name = "running_distance_weight", nullable = false)
    private Double runningDistanceWeight = 1.0;

    @Column(name = "min_hsr_distance")
    private Double minHsrDistance;

    @Column(name = "max_hsr_distance")
    private Double maxHsrDistance;

    @Column(name = "hsr_distance_weight", nullable = false)
    private Double hsrDistanceWeight = 1.0;

    @Column(name = "min_hsr_count")
    private Integer minHsrCount;

    @Column(name = "max_hsr_count")
    private Integer maxHsrCount;

    @Column(name = "hsr_count_weight", nullable = false)
    private Double hsrCountWeight = 1.0;

    @Column(name = "min_sprint_distance")
    private Double minSprintDistance;

    @Column(name = "max_sprint_distance")
    private Double maxSprintDistance;

    @Column(name = "sprint_distance_weight", nullable = false)
    private Double sprintDistanceWeight = 1.0;

    @Column(name = "min_sprint_count")
    private Integer minSprintCount;

    @Column(name = "max_sprint_count")
    private Integer maxSprintCount;

    @Column(name = "sprint_count_weight", nullable = false)
    private Double sprintCountWeight = 1.0;

    @Column(name = "min_hi_distance")
    private Double minHiDistance;

    @Column(name = "max_hi_distance")
    private Double maxHiDistance;

    @Column(name = "hi_distance_weight", nullable = false)
    private Double hiDistanceWeight = 1.0;

    @Column(name = "min_hi_count")
    private Integer minHiCount;

    @Column(name = "max_hi_count")
    private Integer maxHiCount;

    @Column(name = "hi_count_weight", nullable = false)
    private Double hiCountWeight = 1.0;

    @Column(name = "min_psv_99")
    private Double minPsv99;

    @Column(name = "max_psv_99")
    private Double maxPsv99;

    @Column(name = "psv_99_weight", nullable = false)
    private Double psv99Weight = 1.0;

    @Column(name = "min_medium_acceleration_count")
    private Integer minMediumAccelerationCount;

    @Column(name = "max_medium_acceleration_count")
    private Integer maxMediumAccelerationCount;

    @Column(name = "min_high_acceleration_count")
    private Integer minHighAccelerationCount;

    @Column(name = "max_high_acceleration_count")
    private Integer maxHighAccelerationCount;

    @Column(name = "min_medium_deceleration_count")
    private Integer minMediumDecelerationCount;

    @Column(name = "max_medium_deceleration_count")
    private Integer maxMediumDecelerationCount;

    @Column(name = "min_high_deceleration_count")
    private Integer minHighDecelerationCount;

    @Column(name = "max_high_deceleration_count")
    private Integer maxHighDecelerationCount;

    @Column(name = "min_explosive_acceleration_to_hsr_count")
    private Integer minExplosiveAccelerationToHsrCount;

    @Column(name = "max_explosive_acceleration_to_hsr_count")
    private Integer maxExplosiveAccelerationToHsrCount;

    @Column(name = "min_time_to_hsr")
    private Double minTimeToHsr;

    @Column(name = "max_time_to_hsr")
    private Double maxTimeToHsr;

    @Column(name = "min_time_to_hsr_post_cod")
    private Double minTimeToHsrPostCod;

    @Column(name = "max_time_to_hsr_post_cod")
    private Double maxTimeToHsrPostCod;

    @Column(name = "min_explosive_acceleration_to_sprint_count")
    private Integer minExplosiveAccelerationToSprintCount;

    @Column(name = "max_explosive_acceleration_to_sprint_count")
    private Integer maxExplosiveAccelerationToSprintCount;

    @Column(name = "min_time_to_sprint")
    private Double minTimeToSprint;

    @Column(name = "max_time_to_sprint")
    private Double maxTimeToSprint;

    @Column(name = "min_time_to_sprint_post_cod")
    private Double minTimeToSprintPostCod;

    @Column(name = "max_time_to_sprint_post_cod")
    private Double maxTimeToSprintPostCod;

    @Column(name = "min_change_of_direction_count")
    private Integer minChangeOfDirectionCount;

    @Column(name = "max_change_of_direction_count")
    private Integer maxChangeOfDirectionCount;

    @Column(name = "min_time_to_505_around_90")
    private Double minTimeTo505Around90;

    @Column(name = "max_time_to_505_around_90")
    private Double maxTimeTo505Around90;

    @Column(name = "min_time_to_505_around_180")
    private Double minTimeTo505Around180;

    @Column(name = "max_time_to_505_around_180")
    private Double maxTimeTo505Around180;
}
