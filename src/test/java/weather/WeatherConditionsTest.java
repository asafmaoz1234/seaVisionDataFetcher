package weather;

import org.junit.Test;
import weather.conditions.WeatherConditions;
import weather.conditions.impl.MaxConsecutiveInfractions;
import weather.conditions.impl.TotalWaveHeightInfractions;
import weather.pojos.WeatherParsedResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class WeatherConditionsTest {

    @Test
    public void validConditionsForSnorkeling() {
        List<WeatherConditions> conditions = Arrays.asList(new MaxConsecutiveInfractions(),
                new TotalWaveHeightInfractions());
        List<WeatherParsedResult.MetricsPerMeasurment> metrics = new ArrayList<>();
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("1234", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.5)));
        boolean canGo = true;
        for (WeatherConditions condition : conditions) {
            if (!condition.conditionPassed(metrics)) {
                canGo = false;
            }
        }
        assertTrue(canGo);
    }

    @Test
    public void maxConsecutiveInfractions_false() {
        List<WeatherParsedResult.MetricsPerMeasurment> metrics = new ArrayList<>();
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("1234", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.7)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        boolean canGo = new MaxConsecutiveInfractions().conditionPassed(metrics);
        assertFalse(canGo);
    }

    @Test
    public void totalWaveHeightInfractions_false() {
        List<WeatherParsedResult.MetricsPerMeasurment> metrics = new ArrayList<>();
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.1)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.7)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.1)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.7)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.7)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.7)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.1)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.7)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("1234", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.7)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.3)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.1)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.7)));
        boolean canGo = new TotalWaveHeightInfractions().conditionPassed(metrics);
        assertFalse(canGo);
    }
}
