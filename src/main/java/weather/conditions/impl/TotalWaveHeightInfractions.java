package weather.conditions.impl;

import weather.conditions.WeatherConditions;
import weather.pojos.WeatherParsedResult;

import java.util.List;
import java.util.logging.Logger;

import static weather.config.EnvParams.MAX_WAVE_FOR_SNORKEL;
import static weather.config.EnvParams.TOTAL_INFRACTIONS_ALLOWED;

public class TotalWaveHeightInfractions implements WeatherConditions {
    private final Logger logger = Logger.getLogger(TotalWaveHeightInfractions.class.getName());

    @Override
    public boolean conditionPassed(List<WeatherParsedResult.MetricsPerMeasurment> weatherConditions) {
        logger.info("checking: TotalWaveHeightInfractions rule");
        return weatherConditions.stream()
                .filter(waveHeight->waveHeight.getWaveHeight().getNoaa() <= MAX_WAVE_FOR_SNORKEL)
                .count() > TOTAL_INFRACTIONS_ALLOWED;
    }
}
