package weather.client;

import com.amazonaws.HttpMethod;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

import static weather.config.EnvParams.*;

class WeatherConnection {
    private static WeatherConnection weatherConnection = null;
    public HttpURLConnection connection;

    private WeatherConnection() throws IOException {
        String urlString = WEATHER_API_BASE_URL +
                "?lat=" + API_REQUEST_POINT_LAT +
                "&lng=" + API_REQUEST_POINT_LNG +
                "&params=" + API_REQUEST_PARAMS +
                "&source=" + API_REQUEST_SOURCE +
                "&start=" + Instant.now().minus(Duration.ofHours(72)).getEpochSecond() +
                "&end=" + Instant.now().plus(Duration.ofHours(16)).getEpochSecond();
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(HttpMethod.GET.name());
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", API_AUTH_KEY);
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        this.connection = con;
    }

    public static HttpURLConnection getConnection() throws IOException {
        if (weatherConnection == null || weatherConnection.connection == null) {
            weatherConnection = new WeatherConnection();
        }
        return weatherConnection.connection;
    }
}
