package weather;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import weather.client.WeatherClient;
import weather.conditions.WeatherConditions;
import weather.conditions.impl.Snorkeling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

@RunWith(MockitoJUnitRunner.class)
public class HandlerTest {

    @Spy
    WeatherClient weatherClient = spy(new WeatherClient());
    @Spy
    WeatherConditions snorkelingConditions = spy(new Snorkeling());

    @Spy
    Handler handler = spy(new Handler(weatherClient, snorkelingConditions));


    Map<String, String> eventMap;
    Context context = new TestContext();

    @Before
    public void setUp() {
        doReturn(true).when(handler).notifyOnSuccess();
        eventMap = new HashMap<>();
        eventMap.put("key1", "val1");
        eventMap.put("key2", "val2");
    }

    @Test
    public void weatherForSnorkeling_messagePublished() {
        WeatherParsedResult weatherParsedResult = new WeatherParsedResult();
        List<WeatherParsedResult.MetricsPerMeasurment> metrics = new ArrayList<>();
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("1234", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.5)));
        weatherParsedResult.setHours(metrics);
        doReturn(weatherParsedResult).when(weatherClient).fetchWeatherData();
        handler.handleRequest(eventMap, context);
        verify(handler, times(1)).notifyOnSuccess();
    }

    @Test
    public void weatherNOTForSnorkeling_messagePublished() {
        WeatherParsedResult weatherParsedResult = new WeatherParsedResult();
        List<WeatherParsedResult.MetricsPerMeasurment> metrics = new ArrayList<>();
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("1234", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        weatherParsedResult.setHours(metrics);
        doReturn(weatherParsedResult).when(weatherClient).fetchWeatherData();
        handler.handleRequest(eventMap, context);
        verify(handler, never()).notifyOnSuccess();
    }
}
