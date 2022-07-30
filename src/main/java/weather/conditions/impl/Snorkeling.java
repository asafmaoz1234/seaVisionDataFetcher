package weather.conditions.impl;


import weather.WeatherParsedResult;
import weather.conditions.WeatherConditions;

import java.util.List;
import java.util.Optional;

public class Snorkeling implements WeatherConditions {
    private static final Double MAX_WAVE_FOR_SNORKEL = 0.6;

    @Override
    public boolean canGo(List<WeatherParsedResult.MetricsPerMeasurment> weatherConditions) {
        Optional<WeatherParsedResult.MetricsPerMeasurment> minimumConditionsMet = weatherConditions.stream()
                .filter(metricsPerMeasurment -> metricsPerMeasurment.getWaveHeight().getNoaa() > MAX_WAVE_FOR_SNORKEL)
                .findFirst();
        return !minimumConditionsMet.isPresent();
    }
}
