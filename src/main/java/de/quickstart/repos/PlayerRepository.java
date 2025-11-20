package de.quickstart.repos;

import de.quickstart.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("""
    SELECT  other.playerId AS id
    FROM PlayerPerformance me, PlayerPerformance other
    WHERE me.playerId = :playerId
      AND other.playerId <> :playerId
      AND (
            (:wDistance = 0 OR other.totalDistance BETWEEN me.totalDistance * (1 - :tol) AND me.totalDistance * (1 + :tol))
        AND (:wMetersPerMin = 0 OR other.totalMetersPerMin BETWEEN me.totalMetersPerMin * (1 - :tol) AND me.totalMetersPerMin * (1 + :tol))
        AND (:wSprintDistance = 0 OR other.totalSprintDistance BETWEEN me.totalSprintDistance * (1 - :tol) AND me.totalSprintDistance * (1 + :tol))
        AND (:wSprintCount = 0 OR other.totalSprintCount BETWEEN me.totalSprintCount * (1 - :tol) AND me.totalSprintCount * (1 + :tol))
      )
""")
    List<Long> findSimilar(String id);
}
