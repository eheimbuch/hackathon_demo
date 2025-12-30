package de.quickstart.repos;

import de.quickstart.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query(value = """
        WITH
        me AS (
            SELECT *
            FROM player_match_metrics
            WHERE player_id = :playerId           -- Referenzspieler
        ),
        params AS (
            SELECT
                0.10::numeric AS tol,              -- 10% Toleranz

                1.0::numeric AS w_distance,
                0.5::numeric AS w_m_per_min,
                2.0::numeric AS w_sprint_distance,
                1.0::numeric AS w_sprint_count
        )
        SELECT
            other.player_id
        FROM me
        CROSS JOIN params p
        JOIN player_match_metrics other
          ON other.player_id <> me.player_id
        ORDER BY
            (
                p.w_distance * POWER((other.total_distance - me.total_distance)/NULLIF(me.total_distance,0),2)
              + p.w_m_per_min * POWER((other.total_meters_per_min - me.total_meters_per_min)/NULLIF(me.total_meters_per_min,0),2)
              + p.w_sprint_distance * POWER((other.total_sprint_distance - me.total_sprint_distance)/NULLIF(me.total_sprint_distance,0),2)
              + p.w_sprint_count * POWER((other.total_sprint_count - me.total_sprint_count)/NULLIF(me.total_sprint_count,0),2)
            ) ASC
        LIMIT 10;
        """,
            nativeQuery = true)
    List<Long> findSimilar(
            @Param("playerId") Long playerId,
            @Param("wDistance") double wDistance,
            @Param("wMetersPerMin") double wMetersPerMin,
            @Param("wSprintDistance") double wSprintDistance,
            @Param("wSprintCount") double wSprintCount
    );
}
