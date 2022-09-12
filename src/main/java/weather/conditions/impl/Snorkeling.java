package weather.conditions.impl;

import weather.pojos.SnorkelingResult;
import weather.pojos.WeatherParsedResult;
import weather.conditions.WeatherConditions;

import java.util.List;
import java.util.logging.Logger;

import static weather.config.EnvParams.*;

public class Snorkeling implements WeatherConditions {
    private final Logger logger = Logger.getLogger(Snorkeling.class.getName());
    @Override
    public SnorkelingResult analyzeMeasurements(List<WeatherParsedResult.MetricsPerMeasurment> totalMeasurements) {
        int consecutiveInfractions = 0;
        int maxConsecutiveInfractions = 0;
        int totalInfractions = 0;
        logger.info("checking: "+totalMeasurements.size()+" results");

        for (WeatherParsedResult.MetricsPerMeasurment metricsPerMeasurement : totalMeasurements) {
            if(metricsPerMeasurement.getWaveHeight().getNoaa() <= MAX_WAVE_FOR_SNORKEL) {
                consecutiveInfractions = 0;
                continue;
            }
            consecutiveInfractions++;
            totalInfractions++;
            if (consecutiveInfractions > maxConsecutiveInfractions) {
                maxConsecutiveInfractions = consecutiveInfractions;
            }
        }

        logger.info("found: " + totalInfractions + " measurements above min, consecutive above min: " + maxConsecutiveInfractions);
        SnorkelingResult result = new SnorkelingResult(totalMeasurements.size(), totalInfractions, maxConsecutiveInfractions);
        result.setCanGoSnorkeling(maxConsecutiveInfractions < MAX_CONSECUTIVE_INFRACTIONS && totalInfractions < TOTAL_INFRACTIONS_ALLOWED);
        return result;
    }
}
