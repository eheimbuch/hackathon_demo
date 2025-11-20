package de.quickstart;

import java.time.LocalDate;
import java.util.List;

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
    private final MatchPerformance performance;

    public List<PlayerVO> getPlayers() {

        try {
            return playerRepo.findAll()
                    // TODO find only relevant players
                    .stream()
                    .map(player -> {
                        var result = importer.getPlayerMarketValue(player.getFullName());

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
                                performance.getMinutes(),
                                performance.getPhysicalCheckPassed(),
                                performance.getDistance(),
                                performance.getMetersPerMinute(),
                                performance.getRunningDistance(),
                                performance.getHsrDistance(),
                                performance.getHsrCount(),
                                performance.getSprintDistance(),
                                performance.getSprintCount(),
                                performance.getHiDistance(),
                                performance.getHiCount(),
                                performance.getPsv99(),
                                performance.getMediumAccelerationCount(),
                                performance.getHighAccelerationCount(),
                                performance.getMediumDecelerationCount(),
                                performance.getHighDecelerationCount(),
                                performance.getExplosiveAccToHsrCount(),
                                performance.getTimeToHsr(),
                                performance.getTimeToHsrPostCod(),
                                performance.getExplosiveAccToSprintCount(),
                                performance.getTimeToSprint(),
                                performance.getTimeToSprintPostCod(),
                                performance.getChangeOfDirectionCount(),
                                performance.getTimeTo505Around90(),
                                performance.getTimeTo505Around180()
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
            Integer minutes,
            Boolean physicalCheckPassed,
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
            Double timeToSprintPostCod,
            Integer changeOfDirectionCount,
            Double timeTo505Around90,
            Double timeTo505Around180
    ) {}

}
