package weather.conditions.impl;

import weather.WeatherParsedResult;
import weather.conditions.WeatherConditions;

import java.util.List;
import java.util.logging.Logger;

public class Snorkeling implements WeatherConditions {
    private Logger logger = Logger.getLogger(Snorkeling.class.getName());
    private static final Double MAX_WAVE_FOR_SNORKEL = 0.6;
    private static final Integer MAX_CONSECUTIVE_INFRACTIONS = 4;
    private static final Integer TOTAL_INFRACTIONS_ALLOWED = 8;

    @Override
    public boolean canGo(List<WeatherParsedResult.MetricsPerMeasurment> weatherConditions) {
        int consecutiveInfractions = 0;
        int maxConsecutiveInfractions = 0;
        int totalInfractions = 0;

        logger.info("checking: "+weatherConditions.size()+" results");
        for (WeatherParsedResult.MetricsPerMeasurment metricsPerMeasurement : weatherConditions) {
            if (metricsPerMeasurement.getWaveHeight().getNoaa() > MAX_WAVE_FOR_SNORKEL) {
                consecutiveInfractions++;
                totalInfractions++;
                if (consecutiveInfractions > maxConsecutiveInfractions) {
                    maxConsecutiveInfractions = consecutiveInfractions;
                }
            } else {
                consecutiveInfractions = 0;
            }
        }
        logger.info("found: " + totalInfractions + " measurements above min, consecutive above min: " + maxConsecutiveInfractions);
        return maxConsecutiveInfractions < MAX_CONSECUTIVE_INFRACTIONS && totalInfractions < TOTAL_INFRACTIONS_ALLOWED;
    }
}
