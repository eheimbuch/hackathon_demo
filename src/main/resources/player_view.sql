CREATE OR REPLACE VIEW player_match_metrics AS
SELECT
    mp.player_id,
    p.name AS player_name,
    p.short_name,
    avg(mp.minutes) AS total_minutes_per_game,  -- Spielzeit in Minuten pro Spiel
    SUM(mp.minutes) AS total_minutes,  -- Spielzeit in Minuten insgesamt
    avg(mp.distance) AS total_distance,  -- Gesamtlaufstrecke (Meter)
    avg(mp.m_per_min) AS total_meters_per_min,  -- Meter pro Minute
    avg(mp.running_distance) AS total_running_distance,  -- Laufstrecke ohne Sprints (Meter)
    avg(mp.hsr_distance) AS total_hsr_distance,  -- High Speed Running Distance (Meter)
    avg(mp.hsr_count) AS total_hsr_count,  -- Anzahl High Speed Runs (>19,8 km/h)
    avg(mp.sprint_distance) AS total_sprint_distance,  -- Sprintdistanz (>25,2 km/h)
    avg(mp.sprint_count) AS total_sprint_count,  -- Anzahl Sprints
    avg(mp.hi_distance) AS total_hi_distance,  -- High Intensity Distance (Meter)
    avg(mp.hi_count) AS total_hi_count,  -- High Intensity Aktionen
    avg(mp.psv_99) AS total_psv99,  -- PSV 99, z.B. % der maximalen Sprintgeschwindigkeit
    avg(mp.medium_acc_count) AS total_medium_acc_count,  -- mittlere Beschleunigungen
    avg(mp.high_acc_count) AS total_high_acc_count,  -- starke Beschleunigungen
    avg(mp.medium_dec_count) AS total_medium_dec_count,  -- mittlere Verzögerungen / Bremsungen
    avg(mp.high_dec_count) AS total_high_dec_count,  -- starke Verzögerungen / Bremsungen
    avg(mp.expl_acc_to_hsr_count) AS total_expl_acc_to_hsr_count,  -- explosive Beschleunigungen bis HSR
    avg(mp.time_to_hsr) AS total_time_to_hsr,  -- Zeit bis HSR (Sekunden)
    avg(mp.time_to_hsr_post_cod) AS total_time_to_hsr_post_cod,  -- Zeit bis HSR nach Richtungswechsel
    avg(mp.expl_acc_to_sprint_count) AS total_expl_acc_to_sprint_count,  -- explosive Beschleunigungen bis Sprint
    avg(mp.time_to_sprint) AS total_time_to_sprint,  -- Zeit bis Sprint (Sekunden)
    avg(mp.time_to_sprint_post_cod) AS total_time_to_sprint_post_cod,  -- Zeit bis Sprint nach Richtungswechsel
    avg(mp.change_of_direction_count) AS total_cod_count,  -- Anzahl Richtungswechsel
    avg(mp.time_to_505_around_90) AS total_time_to505_90,  -- Zeit für 5-0-5-Test um 90° (Sekunden)
    avg(mp.time_to_505_around_180) AS total_time_to505_180  -- Zeit für 5-0-5-Test um 180° (Sekunden)
FROM match_performances mp
         LEFT JOIN players p ON mp.player_id = p.id
GROUP BY mp.player_id, p.name, p.short_name;


DROP table player_match_metrics;

DROP view player_match_metrics;


ALTER TABLE search_profiles
    ALTER COLUMN distance_weight DROP NOT NULL,
    ALTER COLUMN m_per_min_weight DROP NOT NULL,
    ALTER COLUMN running_distance_weight DROP NOT NULL,
    ALTER COLUMN hsr_distance_weight DROP NOT NULL,
    ALTER COLUMN hsr_count_weight DROP NOT NULL,
    ALTER COLUMN sprint_distance_weight DROP NOT NULL,
    ALTER COLUMN sprint_count_weight DROP NOT NULL,
    ALTER COLUMN hi_distance_weight DROP NOT NULL,
    ALTER COLUMN hi_count_weight DROP NOT NULL,
    ALTER COLUMN psv_99_weight DROP NOT NULL;