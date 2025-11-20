package de.quickstart;

import com.github.pjfanning.xlsx.StreamingReader;
import de.quickstart.models.*;
import de.quickstart.repos.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExcelImporter {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final PositionRepository positionRepository;
    private final CompetitionRepository competitionRepository;
    private final SeasonRepository seasonRepository;
    private final CompetitionEditionRepository competitionEditionRepository;
    private final MatchPerformanceRepository matchPerformanceRepository;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    @Transactional
    public void importExcel() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("skillcorner.xlsx");
             Workbook workbook = StreamingReader.builder()
                     .rowCacheSize(10)
                     .bufferSize(4096)
                     .open(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean header = true;
            List<MatchPerformance> performances = new ArrayList<>();

            for (Row row : sheet) {
                if (header) { header = false; continue; }

                Long playerId = parseLong(get(row, 2));
                Long teamId = parseLong(get(row, 5));
                Long matchId = parseLong(get(row, 7));
                Long competitionId = parseLong(get(row, 10));
                Long seasonId = parseLong(get(row, 12));
                Long editionId = parseLong(get(row, 13));
                String positionName = get(row, 14);
                String positionGroup = get(row, 15);

                Team team = getOrCreateTeam(teamId, row);
                Player player = getOrCreatePlayer(playerId, row, team);
                Season season = getOrCreateSeason(seasonId, row);
                Competition competition = getOrCreateCompetition(competitionId, row);
                CompetitionEdition edition = getOrCreateEdition(editionId, competition, season);
                Match match = getOrCreateMatch(matchId, row, edition, season);
                Position position = getOrCreatePosition(positionName, positionGroup);

                MatchPerformance perf = new MatchPerformance();
                perf.setPlayer(player);
                perf.setTeam(team);
                perf.setMatch(match);
                perf.setPosition(position);
                perf.setCompetitionEdition(edition);

                perf.setMinutes(parseInteger(get(row, 16)));
                perf.setPhysicalCheckPassed(parseBoolean(get(row, 17)));
                perf.setDistance(parseDouble(get(row, 18)));
                perf.setMetersPerMinute(parseDouble(get(row, 19)));
                perf.setRunningDistance(parseDouble(get(row, 20)));
                perf.setHsrDistance(parseDouble(get(row, 21)));
                perf.setHsrCount(parseInteger(get(row, 22)));
                perf.setSprintDistance(parseDouble(get(row, 23)));
                perf.setSprintCount(parseInteger(get(row, 24)));
                perf.setHiDistance(parseDouble(get(row, 25)));
                perf.setHiCount(parseInteger(get(row, 26)));
                perf.setPsv99(parseDouble(get(row, 27)));
                perf.setMediumAccelerationCount(parseInteger(get(row, 28)));
                perf.setHighAccelerationCount(parseInteger(get(row, 29)));
                perf.setMediumDecelerationCount(parseInteger(get(row, 30)));
                perf.setHighDecelerationCount(parseInteger(get(row, 31)));
                perf.setExplosiveAccToHsrCount(parseInteger(get(row, 32)));
                perf.setTimeToHsr(parseDouble(get(row, 33)));
                perf.setTimeToHsrPostCod(parseDouble(get(row, 34)));
                perf.setExplosiveAccToSprintCount(parseInteger(get(row, 35)));
                perf.setTimeToSprint(parseDouble(get(row, 36)));
                perf.setTimeToSprintPostCod(parseDouble(get(row, 37)));
                perf.setChangeOfDirectionCount(parseInteger(get(row, 38)));
                perf.setTimeTo505Around90(parseDouble(get(row, 39)));
                perf.setTimeTo505Around180(parseDouble(get(row, 40)));


                performances.add(perf);
                log.info("process: " + row.getRowNum());
            }

            matchPerformanceRepository.saveAll(performances);
            matchPerformanceRepository.flush();
        }
    }

    private Player getOrCreatePlayer(Long id, Row row, Team team) {
        if (id == null) return null;
        return playerRepository.findById(id).orElseGet(() -> {
            Player p = new Player();
            p.setId(id);
            p.setFullName(get(row, 0));
            p.setShortName(get(row, 1));
            p.setTeam(team);
            Cell birthCell = row.getCell(3);
            if (birthCell != null) {
                if (birthCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(birthCell)) {
                    p.setBirthdate(birthCell.getLocalDateTimeCellValue().toLocalDate());
                } else if (birthCell.getCellType() == CellType.STRING) {
                    try { p.setBirthdate(LocalDate.parse(birthCell.getStringCellValue(), dateFormatter)); }
                    catch (Exception ignored) {}
                }
            }
            return playerRepository.save(p);
        });
    }

    private Team getOrCreateTeam(Long id, Row row) {
        if (id == null) return null;
        return teamRepository.findById(id).orElseGet(() -> {
            Team t = new Team();
            t.setId(id);
            t.setName(get(row, 4));
            return teamRepository.save(t);
        });
    }

    private Match getOrCreateMatch(Long id, Row row, CompetitionEdition competitionEdition, Season season) {
        if (id == null) return null;
        return matchRepository.findById(id).orElseGet(() -> {
            Match m = new Match();
            m.setId(id);
            m.setName(get(row, 6));
            m.setCompetitionEdition(competitionEdition);
            m.setSeason(season);
            Cell dateCell = row.getCell(8);
            if (dateCell != null) {
                if (dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                    m.setDate(dateCell.getLocalDateTimeCellValue().toLocalDate()); // statt LocalDateTime
                } else if (dateCell.getCellType() == CellType.STRING) {
                    try {
                        m.setDate(LocalDate.parse(dateCell.getStringCellValue(), dateFormatter));
                    } catch (Exception ignored) {}
                }
            }
            return matchRepository.save(m);
        });
    }

    private Position getOrCreatePosition(String name, String group) {
        if (name == null) return null;
        return positionRepository.findByName(name).orElseGet(() -> {
            Position p = new Position();
            p.setName(name);
            p.setGroup(group);
            return positionRepository.save(p);
        });
    }

    private Competition getOrCreateCompetition(Long id, Row row) {
        if (id == null) return null;
        return competitionRepository.findById(id).orElseGet(() -> {
            Competition c = new Competition();
            c.setId(id);
            c.setName(get(row, 9));
            return competitionRepository.save(c);
        });
    }

    private Season getOrCreateSeason(Long id, Row row) {
        if (id == null) return null;
        return seasonRepository.findById(id).orElseGet(() -> {
            Season s = new Season();
            s.setId(id);
            s.setName(get(row, 11));
            return seasonRepository.save(s);
        });
    }

    private CompetitionEdition getOrCreateEdition(Long id, Competition competition, Season season) {
        if (id == null) return null;
        return competitionEditionRepository.findById(id).orElseGet(() -> {
            CompetitionEdition ce = new CompetitionEdition();
            ce.setId(id);
            ce.setCompetition(competition);
            ce.setSeason(season);
            return competitionEditionRepository.save(ce);
        });
    }

    public void deleteAll() {
        matchPerformanceRepository.deleteAllInBatch();
        matchRepository.deleteAllInBatch();
        playerRepository.deleteAllInBatch();
        teamRepository.deleteAllInBatch();
        positionRepository.deleteAllInBatch();
        competitionRepository.deleteAllInBatch();
        seasonRepository.deleteAllInBatch();
        competitionEditionRepository.deleteAllInBatch();
    }

    private String get(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) return null;

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                }
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return null;
            default:
                return cell.toString().trim();
        }
    }
    private Long parseLong(String v) {
        if (v == null || v.isEmpty()) return null;
        try { return Long.parseLong(v.replace(".0", "")); } catch (Exception e) { return null; }
    }

    private Integer parseInteger(String s) {
        if (s == null || s.isEmpty()) return null;
        try { return Integer.parseInt(s.replace(".0", "")); } catch (NumberFormatException e) { return null; }
    }

    private Double parseDouble(String s) {
        if (s == null || s.isEmpty()) return null;
        try { return Double.parseDouble(s); } catch (NumberFormatException e) { return null; }
    }

    private Boolean parseBoolean(String s) {
        if (s == null || s.isEmpty()) return null;
        return s.equalsIgnoreCase("true") || s.equals("1");
    }
}
