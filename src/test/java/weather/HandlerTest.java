package weather;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import weather.client.WeatherClient;
import weather.exceptions.ClientException;
import weather.pojos.SnorkelingResult;
import weather.pojos.WeatherParsedResult;

import java.util.*;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class HandlerTest {

    @Spy
    WeatherClient weatherClient;

    @Spy
    @InjectMocks
    Handler handler;


    Map<String, String> eventMap = new HashMap<>();
    Context context = new TestContext();
    Gson gson = new Gson();

    @Before
    public void setUp() {
        doReturn(true).when(handler).notifyOnSuccess();
        doReturn(true).when(handler).notifyOnError(any());
    }

    @Ignore
    @Test
    public void testActualData() {
        String result = handler.handleRequest(eventMap, context);
        SnorkelingResult snorkelingResult = gson.fromJson(result, SnorkelingResult.class);
        System.out.println(snorkelingResult);
    }

    @Test
    public void clientThrewException_errorMessagePublished() throws ClientException {
        doThrow(ClientException.class).when(weatherClient).fetchWeatherData();
        handler.handleRequest(eventMap, context);
        verify(handler, times(1)).notifyOnError(any());
        verify(handler, never()).notifyOnSuccess();
    }

    @Test
    public void weatherForSnorkeling_messagePublished() throws ClientException {
        List<WeatherParsedResult.MetricsPerMeasurment> metrics = new ArrayList<>();
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("1234", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.5)));
        doReturn(metrics).when(weatherClient).fetchWeatherData();
        handler.handleRequest(eventMap, context);
        verify(handler, times(1)).notifyOnSuccess();
    }

    @Test
    public void weatherNOTForSnorkeling_messagePublished() throws ClientException {
        List<WeatherParsedResult.MetricsPerMeasurment> metrics = new ArrayList<>();
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("1234", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12345", new WeatherParsedResult.WeatherParamData(0.5)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        metrics.add(new WeatherParsedResult.MetricsPerMeasurment("12346", new WeatherParsedResult.WeatherParamData(0.9)));
        doReturn(metrics).when(weatherClient).fetchWeatherData();
        handler.handleRequest(eventMap, context);
        verify(handler, never()).notifyOnSuccess();
    }
}
