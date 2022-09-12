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
        assertThat(url.getQuery(), containsString("lat="));
        assertThat(url.getQuery(), containsString("&lng="));
        assertThat(url.getQuery(), containsString("&params="));
        assertThat(url.getQuery(), containsString("&source=noaa"));
    }

    @Test
    public void connectionParams() throws IOException {
        HttpURLConnection weatherConnection = WeatherConnection.getConnection();
        assertThat(weatherConnection.getRequestMethod(), is(equalTo("GET")));
        assertThat(weatherConnection.getConnectTimeout(), is(equalTo(5000)));
    }
}
