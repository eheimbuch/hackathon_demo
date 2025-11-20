package de.quickstart.repos;

import de.quickstart.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchPerformanceRepository extends JpaRepository<MatchPerformance, Long> {}
