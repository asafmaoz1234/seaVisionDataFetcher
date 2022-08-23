package weather.conditions;

import weather.pojos.SnorkelingResult;
import weather.pojos.WeatherParsedResult;

import java.util.List;

public interface WeatherConditions {
    SnorkelingResult analyzeMeasurements(List<WeatherParsedResult.MetricsPerMeasurment> weatherConditions);
}
