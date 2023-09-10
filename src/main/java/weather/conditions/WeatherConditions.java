package weather.conditions;

import weather.pojos.WeatherParsedResult;

import java.util.List;

public interface WeatherConditions {
    boolean conditionPassed(List<WeatherParsedResult.MetricsPerMeasurment> weatherConditions);
}
