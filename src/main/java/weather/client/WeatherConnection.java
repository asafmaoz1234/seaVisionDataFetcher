package weather.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

class WeatherConnection {
    private static WeatherConnection weatherConnection = null;
    public HttpURLConnection connection;
    private final String baseUrl = "https://api.stormglass.io/v2/weather/point";
    private final String lat = "32.578070";
    private final String lng = "34.908739";
    private final String params = "waveHeight";
    private final String source = "noaa";

    private WeatherConnection() throws IOException {
        URL url = new URL(baseUrl +
                "?lat=" + lat +
                "&lng=" + lng +
                "&params=" + params +
                "&source=" + source +
                "&start=" + Instant.now().minus(Duration.ofHours(72)).getEpochSecond() +
                "&end=" + Instant.now().getEpochSecond());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", System.getenv("Authorization"));
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        this.connection = con;
    }

    public static HttpURLConnection getConnection() throws IOException {
        if (weatherConnection == null) {
            weatherConnection = new WeatherConnection();
        }
        return weatherConnection.connection;
    }
}
