package de.quickstart.repos;

import de.quickstart.models.SearchProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchProfileRepository extends JpaRepository<SearchProfile, Long> {
}
