package de.quickstart;

import de.quickstart.models.PerformanceFilters;

public final class PerformanceFiltersMapper {

    private PerformanceFiltersMapper() {}

    public static SearchProfileController.PerformanceFiltersRecord toRecord(PerformanceFilters e) {
        if (e == null) return null;

        return new SearchProfileController.PerformanceFiltersRecord(
                e.getMinDistance(),
                e.getMaxDistance(),
                e.getDistanceWeight(),

                e.getMinMPerMin(),
                e.getMaxMPerMin(),
                e.getMPerMinWeight(),

                e.getMinRunningDistance(),
                e.getMaxRunningDistance(),
                e.getRunningDistanceWeight(),

                e.getMinHsrDistance(),
                e.getMaxHsrDistance(),
                e.getHsrDistanceWeight(),

                e.getMinHsrCount(),
                e.getMaxHsrCount(),
                e.getHsrCountWeight(),

                e.getMinSprintDistance(),
                e.getMaxSprintDistance(),
                e.getSprintDistanceWeight(),

                e.getMinSprintCount(),
                e.getMaxSprintCount(),
                e.getSprintCountWeight(),

                e.getMinHiDistance(),
                e.getMaxHiDistance(),
                e.getHiDistanceWeight(),

                e.getMinHiCount(),
                e.getMaxHiCount(),
                e.getHiCountWeight(),

                e.getMinPsv99(),
                e.getMaxPsv99(),
                e.getPsv99Weight(),

                e.getMinMediumAccelerationCount(),
                e.getMaxMediumAccelerationCount(),

                e.getMinHighAccelerationCount(),
                e.getMaxHighAccelerationCount(),

                e.getMinMediumDecelerationCount(),
                e.getMaxMediumDecelerationCount(),

                e.getMinHighDecelerationCount(),
                e.getMaxHighDecelerationCount(),

                e.getMinExplosiveAccelerationToHsrCount(),
                e.getMaxExplosiveAccelerationToHsrCount(),

                e.getMinTimeToHsr(),
                e.getMaxTimeToHsr(),

                e.getMinTimeToHsrPostCod(),
                e.getMaxTimeToHsrPostCod(),

                e.getMinExplosiveAccelerationToSprintCount(),
                e.getMaxExplosiveAccelerationToSprintCount(),

                e.getMinTimeToSprint(),
                e.getMaxTimeToSprint(),

                e.getMinTimeToSprintPostCod(),
                e.getMaxTimeToSprintPostCod(),

                e.getMinChangeOfDirectionCount(),
                e.getMaxChangeOfDirectionCount(),

                e.getMinTimeTo505Around90(),
                e.getMaxTimeTo505Around90(),

                e.getMinTimeTo505Around180(),
                e.getMaxTimeTo505Around180()
        );
    }

    public static PerformanceFilters toEntity(SearchProfileController.PerformanceFiltersRecord r) {
        if (r == null) return null;

        PerformanceFilters e = new PerformanceFilters();

        e.setMinDistance(r.minDistance());
        e.setMaxDistance(r.maxDistance());
        e.setDistanceWeight(defaultWeight(r.distanceWeight()));

        e.setMinMPerMin(r.minMPerMin());
        e.setMaxMPerMin(r.maxMPerMin());
        e.setMPerMinWeight(defaultWeight(r.mPerMinWeight()));

        e.setMinRunningDistance(r.minRunningDistance());
        e.setMaxRunningDistance(r.maxRunningDistance());
        e.setRunningDistanceWeight(defaultWeight(r.runningDistanceWeight()));

        e.setMinHsrDistance(r.minHsrDistance());
        e.setMaxHsrDistance(r.maxHsrDistance());
        e.setHsrDistanceWeight(defaultWeight(r.hsrDistanceWeight()));

        e.setMinHsrCount(r.minHsrCount());
        e.setMaxHsrCount(r.maxHsrCount());
        e.setHsrCountWeight(defaultWeight(r.hsrCountWeight()));

        e.setMinSprintDistance(r.minSprintDistance());
        e.setMaxSprintDistance(r.maxSprintDistance());
        e.setSprintDistanceWeight(defaultWeight(r.sprintDistanceWeight()));

        e.setMinSprintCount(r.minSprintCount());
        e.setMaxSprintCount(r.maxSprintCount());
        e.setSprintCountWeight(defaultWeight(r.sprintCountWeight()));

        e.setMinHiDistance(r.minHiDistance());
        e.setMaxHiDistance(r.maxHiDistance());
        e.setHiDistanceWeight(defaultWeight(r.hiDistanceWeight()));

        e.setMinHiCount(r.minHiCount());
        e.setMaxHiCount(r.maxHiCount());
        e.setHiCountWeight(defaultWeight(r.hiCountWeight()));

        e.setMinPsv99(r.minPsv99());
        e.setMaxPsv99(r.maxPsv99());
        e.setPsv99Weight(defaultWeight(r.psv99Weight()));

        e.setMinMediumAccelerationCount(r.minMediumAccelerationCount());
        e.setMaxMediumAccelerationCount(r.maxMediumAccelerationCount());

        e.setMinHighAccelerationCount(r.minHighAccelerationCount());
        e.setMaxHighAccelerationCount(r.maxHighAccelerationCount());

        e.setMinMediumDecelerationCount(r.minMediumDecelerationCount());
        e.setMaxMediumDecelerationCount(r.maxMediumDecelerationCount());

        e.setMinHighDecelerationCount(r.minHighDecelerationCount());
        e.setMaxHighDecelerationCount(r.maxHighDecelerationCount());

        e.setMinExplosiveAccelerationToHsrCount(r.minExplosiveAccelerationToHsrCount());
        e.setMaxExplosiveAccelerationToHsrCount(r.maxExplosiveAccelerationToHsrCount());

        e.setMinTimeToHsr(r.minTimeToHsr());
        e.setMaxTimeToHsr(r.maxTimeToHsr());

        e.setMinTimeToHsrPostCod(r.minTimeToHsrPostCod());
        e.setMaxTimeToHsrPostCod(r.maxTimeToHsrPostCod());

        e.setMinExplosiveAccelerationToSprintCount(r.minExplosiveAccelerationToSprintCount());
        e.setMaxExplosiveAccelerationToSprintCount(r.maxExplosiveAccelerationToSprintCount());

        e.setMinTimeToSprint(r.minTimeToSprint());
        e.setMaxTimeToSprint(r.maxTimeToSprint());

        e.setMinTimeToSprintPostCod(r.minTimeToSprintPostCod());
        e.setMaxTimeToSprintPostCod(r.maxTimeToSprintPostCod());

        e.setMinChangeOfDirectionCount(r.minChangeOfDirectionCount());
        e.setMaxChangeOfDirectionCount(r.maxChangeOfDirectionCount());

        e.setMinTimeTo505Around90(r.minTimeTo505Around90());
        e.setMaxTimeTo505Around90(r.maxTimeTo505Around90());

        e.setMinTimeTo505Around180(r.minTimeTo505Around180());
        e.setMaxTimeTo505Around180(r.maxTimeTo505Around180());

        return e;
    }

    // helper for null → default = 1.0
    private static Double defaultWeight(Double value) {
        return value != null ? value : 1.0;
    }
}
