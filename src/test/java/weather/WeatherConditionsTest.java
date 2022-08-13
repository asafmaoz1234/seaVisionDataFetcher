package weather;

import org.junit.Test;
import weather.conditions.impl.Snorkeling;
import weather.pojos.SnorkelingResult;
import weather.pojos.WeatherParsedResult;

import java.util.Stack;

import static org.junit.Assert.*;


public class WeatherConditionsTest {

    Snorkeling snorkeling = new Snorkeling();

    @Test
    public void validConditionsForSnorkeling() {
        Stack<WeatherParsedResult.MetricsPerMeasurment> metrics = new Stack<>();
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
        Stack<WeatherParsedResult.MetricsPerMeasurment> metrics = new Stack<>();
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
        Stack<WeatherParsedResult.MetricsPerMeasurment> metrics = new Stack<>();
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
