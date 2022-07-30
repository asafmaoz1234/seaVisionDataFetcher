package weather.client;

import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class WeatherClientTests {
    @Spy
    WeatherClient weatherClient = spy(new WeatherClient());

}
