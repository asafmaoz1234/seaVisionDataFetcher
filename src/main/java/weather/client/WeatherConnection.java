package weather.client;

import com.amazonaws.HttpMethod;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

class WeatherConnection {
    private static WeatherConnection weatherConnection = null;
    public HttpURLConnection connection;
    private final String baseUrl = "https://api.stormglass.io/v2/weather/point";
    private final String authKey = System.getenv("Authorization");
    private final String lat = "32.578070";
    private final String lng = "34.908739";
    private final String params = "waveHeight";
    private final String source = "noaa";

    private WeatherConnection() throws IOException {
        String urlString = this.baseUrl +
                "?lat=" + lat +
                "&lng=" + lng +
                "&params=" + params +
                "&source=" + source +
                "&start=" + Instant.now().minus(Duration.ofHours(72)).getEpochSecond() +
                "&end=" + Instant.now().getEpochSecond();
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(HttpMethod.GET.name());
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", this.authKey);
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
