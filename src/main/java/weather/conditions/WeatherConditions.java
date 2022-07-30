package weather.conditions;

import weather.WeatherParsedResult;

import java.util.List;

public interface WeatherConditions {
    boolean canGo(List<WeatherParsedResult.MetricsPerMeasurment> weatherConditions);
}
