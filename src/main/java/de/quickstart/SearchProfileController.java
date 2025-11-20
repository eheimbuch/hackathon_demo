package de.quickstart;

import de.quickstart.models.Player;
import de.quickstart.models.SearchProfile;
import de.quickstart.repos.PlayerRepository;
import de.quickstart.repos.SearchProfileRepository;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "api/search-profiles")
@AllArgsConstructor
public class SearchProfileController {

    private final SearchProfileRepository searchProfileRepository;
    private final PlayerRepository playerRepository;

    @PostMapping
    public void create(@RequestBody SearchProfileRecord searchProfile) {
        SearchProfile profile = new SearchProfile(
                null,
                searchProfile.name(),
                searchProfile.position(),
                searchProfile.positionGroup(),
                searchProfile.minAge(),
                searchProfile.maxAge(),
                List.of(),
                PerformanceFiltersMapper.toEntity(searchProfile.performanceFilters())
        );
        searchProfileRepository.save(profile);
        searchProfileRepository.flush();
    }

    @PutMapping("/{id}")
    public SearchProfileRecord update(@PathParam("id") Long id, @RequestBody SearchProfileRecord searchProfile) {
        SearchProfile p = searchProfileRepository.findById(id).orElseThrow();
        p.setName(searchProfile.name());
        p.setPosition(searchProfile.position());
        p.setPositionGroup(searchProfile.positionGroup());
        p.setMinAge(searchProfile.minAge());
        p.setMaxAge(searchProfile.maxAge());
        p.setPerformanceFilters(PerformanceFiltersMapper.toEntity(searchProfile.performanceFilters()));
        searchProfileRepository.save(p);
        searchProfileRepository.flush();

        return searchProfile;
    }

    @PutMapping("/{search_profile_id}/players/{player_id}")
    public void addFavorite(@PathVariable("search_profile_id") Long searchProfileId, @PathVariable("player_id") Long playerId) {
        SearchProfile p = searchProfileRepository.findById(searchProfileId).orElseThrow();
        Player player = playerRepository.findById(playerId).orElseThrow();
        p.getFavoritePlayers().add(player);
        playerRepository.flush();
    }

    @GetMapping("{search_profile_id}/players")
    public List<PlayerControler.PlayerVO> getShortList(@PathVariable("search_profile_id") Long searchProfileId) {
        SearchProfile p = searchProfileRepository.findById(searchProfileId).orElseThrow();

        return p.getFavoritePlayers().stream()
                .map(pl -> new PlayerControler.PlayerVO(pl.getFullName(), 0, 12))
                .toList();
    }


    @GetMapping
    public List<SearchProfileRecord> findAll() {
        return searchProfileRepository.findAll().stream()
                .map(p -> new SearchProfileRecord(p.getId(), p.getName(), p.getPosition(), p.getPositionGroup(), p.getMinAge(), p.getMaxAge(), PerformanceFiltersMapper.toRecord(p.getPerformanceFilters())))
                .toList();
    }

    public record PerformanceFiltersRecord(
            Double minDistance,
            Double maxDistance,
            Double distanceWeight,

            Double minMPerMin,
            Double maxMPerMin,
            Double mPerMinWeight,

            Double minRunningDistance,
            Double maxRunningDistance,
            Double runningDistanceWeight,

            Double minHsrDistance,
            Double maxHsrDistance,
            Double hsrDistanceWeight,

            Integer minHsrCount,
            Integer maxHsrCount,
            Double hsrCountWeight,

            Double minSprintDistance,
            Double maxSprintDistance,
            Double sprintDistanceWeight,

            Integer minSprintCount,
            Integer maxSprintCount,
            Double sprintCountWeight,

            Double minHiDistance,
            Double maxHiDistance,
            Double hiDistanceWeight,

            Integer minHiCount,
            Integer maxHiCount,
            Double hiCountWeight,

            Double minPsv99,
            Double maxPsv99,
            Double psv99Weight,

            Integer minMediumAccelerationCount,
            Integer maxMediumAccelerationCount,

            Integer minHighAccelerationCount,
            Integer maxHighAccelerationCount,

            Integer minMediumDecelerationCount,
            Integer maxMediumDecelerationCount,

            Integer minHighDecelerationCount,
            Integer maxHighDecelerationCount,

            Integer minExplosiveAccelerationToHsrCount,
            Integer maxExplosiveAccelerationToHsrCount,

            Double minTimeToHsr,
            Double maxTimeToHsr,

            Double minTimeToHsrPostCod,
            Double maxTimeToHsrPostCod,

            Integer minExplosiveAccelerationToSprintCount,
            Integer maxExplosiveAccelerationToSprintCount,

            Double minTimeToSprint,
            Double maxTimeToSprint,

            Double minTimeToSprintPostCod,
            Double maxTimeToSprintPostCod,

            Integer minChangeOfDirectionCount,
            Integer maxChangeOfDirectionCount,

            Double minTimeTo505Around90,
            Double maxTimeTo505Around90,

            Double minTimeTo505Around180,
            Double maxTimeTo505Around180
    ) {}

    public record SearchProfileRecord(
            Long id,
            String name,
            String position,
            String positionGroup,
            Integer minAge,
            Integer maxAge,
            PerformanceFiltersRecord performanceFilters
    ) {}
}
