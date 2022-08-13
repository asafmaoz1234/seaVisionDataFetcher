package weather.conditions;

import weather.pojos.SnorkelingResult;
import weather.pojos.WeatherParsedResult;

import java.util.Stack;

public interface WeatherConditions {
    SnorkelingResult canGo(Stack<WeatherParsedResult.MetricsPerMeasurment> weatherConditions);
}
