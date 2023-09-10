package weather.conditions.impl;

import weather.conditions.WeatherConditions;
import weather.pojos.WeatherParsedResult;

import java.util.List;
import java.util.logging.Logger;

import static weather.config.EnvParams.MAX_CONSECUTIVE_INFRACTIONS;
import static weather.config.EnvParams.MAX_WAVE_FOR_SNORKEL;

public class MaxConsecutiveInfractions implements WeatherConditions {
    private final Logger logger = Logger.getLogger("MaxConsecutiveInfractions");

    @Override
    public boolean conditionPassed(List<WeatherParsedResult.MetricsPerMeasurment> weatherConditions) {
        logger.info("checking: MaxConsecutiveInfractions rule");
        int consecutiveInfractions = 0;
        int maxConsecutiveInfractions = 0;
        for (WeatherParsedResult.MetricsPerMeasurment metricsPerMeasurement : weatherConditions) {
            if(metricsPerMeasurement.getWaveHeight().getNoaa() <= MAX_WAVE_FOR_SNORKEL) {
                consecutiveInfractions = 0;
                continue;
            }
            consecutiveInfractions++;
            maxConsecutiveInfractions = Math.max(consecutiveInfractions, maxConsecutiveInfractions);
        }
        return maxConsecutiveInfractions < MAX_CONSECUTIVE_INFRACTIONS;
    }
}
