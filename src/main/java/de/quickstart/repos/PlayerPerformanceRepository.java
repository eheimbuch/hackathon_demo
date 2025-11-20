package de.quickstart.repos;

import de.quickstart.models.PlayerPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerPerformanceRepository extends JpaRepository<PlayerPerformance, Long> {}

