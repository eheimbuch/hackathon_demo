package de.quickstart;

import java.time.LocalDate;
import java.util.List;

import de.quickstart.models.PlayerPerformance;
import de.quickstart.repos.MatchPerformanceRepository;
import de.quickstart.repos.PlayerPerformanceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.quickstart.models.MatchPerformance;
import de.quickstart.repos.PlayerRepository;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PlayerControler {

    private final PlayerRepository playerRepo;
    private final TransfermarktImport importer;
    private final PlayerPerformanceRepository performanceRepository;

    @GetMapping("/api/players/")
    public List<PlayerVO> getPlayers() {

        try {
            return playerRepo.findAll()
                    // TODO find only relevant players
                    .stream()
                    .limit(10)
                    .map(player -> {
                        var result = importer.getPlayerMarketValue(player.getFullName());
                        PlayerPerformance performance = performanceRepository.findById(player.getId()).orElseThrow();

                        return new PlayerVO(
                                player.getFullName(),
                                player.getShortName(),
                                player.getTeam() != null ? player.getTeam().getName() : null,
                                player.getBirthdate(),
                                player.getId(),
                                result.marketValue(),
                                result.age(),
                                result.nationality(),
                                result.imgurl(),

                                // --- MatchPerformance-Werte ---
                                performance.getTotalMinutes(),
                                performance.getTotalDistance(),
                                performance.getTotalMetersPerMin(),
                                performance.getTotalRunningDistance(),
                                performance.getTotalHsrDistance(),
                                performance.getTotalHsrCount(),
                                performance.getTotalSprintDistance(),
                                performance.getTotalSprintCount(),
                                performance.getTotalHiDistance(),
                                performance.getTotalHiCount(),
                                performance.getTotalPsv99(),
                                performance.getTotalMediumAccCount(),
                                performance.getTotalHighAccCount(),
                                performance.getTotalMediumDecCount(),
                                performance.getTotalHighDecCount(),
                                performance.getTotalExplAccToHsrCount(),
                                performance.getTotalTimeToHsr(),
                                performance.getTotalTimeToHsrPostCod(),
                                performance.getTotalExplAccToSprintCount(),
                                performance.getTotalTimeToSprint(),
                                performance.getTotalTimeToSprintPostCod()
                        );
                    })
                    .toList();
        } catch (Exception e) {
            System.err.println(e);
            return List.of();
        }

    }

    public record PlayerVO(
            String name,
            String shortName,
            String teamName,
            LocalDate birthdate,
            Long playerId,
            long marketvalue,
            int age,
            String nationality,
            String imgurl,

            // MatchPerformance-Felder
            Double minutes,
            Double distance,
            Double metersPerMinute,
            Double runningDistance,
            Double hsrDistance,
            Integer hsrCount,
            Double sprintDistance,
            Integer sprintCount,
            Double hiDistance,
            Integer hiCount,
            Double psv99,
            Integer mediumAccelerationCount,
            Integer highAccelerationCount,
            Integer mediumDecelerationCount,
            Integer highDecelerationCount,
            Integer explosiveAccToHsrCount,
            Double timeToHsr,
            Double timeToHsrPostCod,
            Integer explosiveAccToSprintCount,
            Double timeToSprint,
            Double timeToSprintPostCod
    ) {}

}
