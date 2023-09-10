package weather.conditions.impl;

import weather.conditions.WeatherConditions;
import weather.pojos.WeatherParsedResult;

import java.util.List;

public class LastDayInfractions implements WeatherConditions {

    @Override
    public boolean conditionPassed(List<WeatherParsedResult.MetricsPerMeasurment> weatherConditions) {
        return true;
    }
}
