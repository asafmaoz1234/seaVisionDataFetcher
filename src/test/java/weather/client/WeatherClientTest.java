package weather.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import weather.exceptions.ClientException;
import weather.pojos.WeatherParsedResult;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class WeatherClientTest {
    @Spy
    WeatherClient weatherClient = spy(new WeatherClient());
    InputStreamReader targetStream;

    @Before
    public void setUP() throws IOException {
        this.targetStream = new InputStreamReader(Files.newInputStream(new File("src/test/resources/sample_response.txt").toPath()));
        doReturn(this.targetStream).when(weatherClient).getInputStreamFromWeatherEndpoint();
    }

    @Test
    public void validResponse_validParse() throws ClientException {
        List<WeatherParsedResult.MetricsPerMeasurment> response = weatherClient.fetchWeatherData();
        assertEquals(response.size(), 73);
    }
}
