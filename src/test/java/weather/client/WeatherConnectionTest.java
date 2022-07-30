package weather.client;

import org.junit.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class WeatherConnectionTest {

    @Test
    public void connectionUrlSetUpCorrectly() throws IOException {
        HttpURLConnection weatherConnection = WeatherConnection.getConnection();
        URL url = weatherConnection.getURL();
        assertThat(url.getProtocol(), is(equalTo("https")));
        assertThat(url.getHost(), is(equalTo("api.stormglass.io")));
        assertThat(url.getQuery(), containsString("lat=32.578070&lng=34.908739&params=waveHeight&source=noaa"));
    }

    @Test
    public void connectionParams() throws IOException {
        HttpURLConnection weatherConnection = WeatherConnection.getConnection();
        assertThat(weatherConnection.getRequestMethod(), is(equalTo("GET")));
        assertThat(weatherConnection.getConnectTimeout(), is(equalTo(5000)));
    }
}
