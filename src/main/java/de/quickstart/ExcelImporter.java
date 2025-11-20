package de.quickstart;

import de.quickstart.models.*;
import de.quickstart.repos.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ExcelImporter {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final PositionRepository positionRepository;
    private final CompetitionRepository competitionRepository;
    private final SeasonRepository seasonRepository;
    private final CompetitionEditionRepository competitionEditionRepository;
    private final MatchPerformanceRepository matchPerformanceRepository;

    public void importExcel(InputStream inputStream) throws Exception {
        Workbook workbook = null;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("skillcorner.xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            boolean header = true;
            for (Row row : sheet) {
                if (header) {
                    header = false;
                    continue;
                }

                String playerName = get(row, 0);
                String shortName = get(row, 1);
                Long playerId = parseLong(get(row, 2));
                String birthdate = get(row, 3);
                String teamName = get(row, 4);
                Long teamId = parseLong(get(row, 5));
                String matchName = get(row, 6);
                Long matchId = parseLong(get(row, 7));
                String date = get(row, 8);
                String competitionName = get(row, 9);
                Long competitionId = parseLong(get(row, 10));
                String seasonName = get(row, 11);
                Long seasonId = parseLong(get(row, 12));
                Long competitionEditionId = parseLong(get(row, 13));
                String positionName = get(row, 14);
                String positionGroup = get(row, 15);

                Player player = playerRepository.findById(playerId).orElseGet(() -> {
                    Player p = new Player();
                    p.setId(playerId);
                    p.setFullName(playerName);
                    p.setShortName(shortName);
                    p.setBirthdate(LocalDate.parse(birthdate));
                    return playerRepository.save(p);
                });

                Team team = teamRepository.findById(teamId).orElseGet(() -> {
                    Team t = new Team();
                    t.setId(teamId);
                    t.setName(teamName);
                    return teamRepository.save(t);
                });

                Match match = matchRepository.findById(matchId).orElseGet(() -> {
                    Match m = new Match();
                    m.setId(matchId);
                    m.setName(matchName);
                    if (date != null) {
                        m.setDate(LocalDateTime.parse(date));
                    }
                    return matchRepository.save(m);
                });

                Position position = positionRepository.findByName(positionName).orElseGet(() -> {
                    Position p = new Position();
                    p.setName(positionName);
                    p.setGroup(positionGroup);
                    return positionRepository.save(p);
                });

                Competition competition = competitionRepository.findById(competitionId).orElseGet(() -> {
                    Competition c = new Competition();
                    c.setId(competitionId);
                    c.setName(competitionName);
                    return competitionRepository.save(c);
                });

                Season season = seasonRepository.findById(seasonId).orElseGet(() -> {
                    Season s = new Season();
                    s.setId(seasonId);
                    s.setName(seasonName);
                    return seasonRepository.save(s);
                });

                CompetitionEdition edition = competitionEditionRepository.findById(competitionEditionId).orElseGet(() -> {
                    CompetitionEdition ce = new CompetitionEdition();
                    ce.setId(competitionEditionId);
                    ce.setSeason(season);
                    ce.setCompetition(competition);
                    return competitionEditionRepository.save(ce);
                });

                MatchPerformance perf = new MatchPerformance();
                perf.setPlayer(player);
                perf.setTeam(team);
                perf.setMatch(match);
                perf.setPosition(position);
                perf.setCompetitionEdition(edition);

                matchPerformanceRepository.save(perf);
            }

        } finally {
            workbook.close();
        }


    }

    private String get(Row row, int cell) {
        Cell c = row.getCell(cell);
        if (c == null) return null;
        return c.toString().trim();
    }

    private Long parseLong(String v) {
        if (v == null || v.isEmpty()) return null;
        try {
            return Long.parseLong(v.replace(".0", ""));
        } catch (Exception e) {
            return null;
        }
    }
}
