CREATE OR REPLACE VIEW player_match_metrics AS
SELECT
    mp.player_id,
    p.name AS player_name,
    p.short_name,
    SUM(mp.minutes) AS total_minutes,
    SUM(mp.distance) AS total_distance,
    SUM(mp.m_per_min) AS total_meters_per_min,
    SUM(mp.running_distance) AS total_running_distance,
    SUM(mp.hsr_distance) AS total_hsr_distance,
    SUM(mp.hsr_count) AS total_hsr_count,
    SUM(mp.sprint_distance) AS total_sprint_distance,
    SUM(mp.sprint_count) AS total_sprint_count,
    SUM(mp.hi_distance) AS total_hi_distance,
    SUM(mp.hi_count) AS total_hi_count,
    SUM(mp.psv_99) AS total_psv_99,
    SUM(mp.medium_acc_count) AS total_medium_acc_count,
    SUM(mp.high_acc_count) AS total_high_acc_count,
    SUM(mp.medium_dec_count) AS total_medium_dec_count,
    SUM(mp.high_dec_count) AS total_high_dec_count,
    SUM(mp.expl_acc_to_hsr_count) AS total_expl_acc_to_hsr_count,
    SUM(mp.time_to_hsr) AS total_time_to_hsr,
    SUM(mp.time_to_hsr_post_cod) AS total_time_to_hsr_post_cod,
    SUM(mp.expl_acc_to_sprint_count) AS total_expl_acc_to_sprint_count,
    SUM(mp.time_to_sprint) AS total_time_to_sprint,
    SUM(mp.time_to_sprint_post_cod) AS total_time_to_sprint_post_cod,
    SUM(mp.change_of_direction_count) AS total_cod_count,
    SUM(mp.time_to_505_around_90) AS total_time_to_505_90,
    SUM(mp.time_to_505_around_180) AS total_time_to_505_180
FROM match_performances mp
         LEFT JOIN players p ON mp.player_id = p.id
GROUP BY mp.player_id, p.name, p.short_name;
