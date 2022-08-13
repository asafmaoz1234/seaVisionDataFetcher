package weather;

import org.junit.Test;
import weather.conditions.impl.Snorkeling;
import weather.pojos.SnorkelingResult;
import weather.pojos.WeatherParsedResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class WeatherConditionsTest {

    Snorkeling snorkeling = new Snorkeling();

    @Test
    public void validConditionsForSnorkeling() {
        List<WeatherParsedResult.MetricsPerMeasurment> metrics = new ArrayList<>();
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("1234", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.5)));
        SnorkelingResult snorkelingResult = snorkeling.canGo(metrics);
        assertTrue(snorkelingResult.canGo());
        assertEquals((Integer) metrics.size(), snorkelingResult.getReadingCount());
        assertEquals((Integer) 0, snorkelingResult.getConsecutiveReadingsAboveMinimum());
    }

    @Test
    public void aboveMaxConsecutiveInfractions_false() {
        List<WeatherParsedResult.MetricsPerMeasurment> metrics = new ArrayList<>();
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("1234", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.7)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        SnorkelingResult snorkelingResult = snorkeling.canGo(metrics);
        assertFalse(snorkelingResult.canGo());
        assertEquals((Integer) metrics.size(), snorkelingResult.getReadingCount());
        assertEquals((Integer) 4, snorkelingResult.getConsecutiveReadingsAboveMinimum());
    }

    @Test
    public void aboveTotalInfractions_false() {
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
        SnorkelingResult snorkelingResult = snorkeling.canGo(metrics);
        assertFalse(snorkelingResult.canGo());
        assertEquals((Integer) metrics.size(), snorkelingResult.getReadingCount());
        assertEquals((Integer) 3, snorkelingResult.getConsecutiveReadingsAboveMinimum());
    }
}
