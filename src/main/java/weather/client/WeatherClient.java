package weather.client;

import com.google.gson.Gson;
import weather.WeatherParsedResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WeatherClient {
    Gson gson = new Gson();

    InputStreamReader getInputStreamFromWeatherEndpoint() throws IOException {
        return new InputStreamReader(WeatherConnection.getConnection().getInputStream());
    }

    public WeatherParsedResult fetchWeatherData() {
        StringBuilder content;
        try {
            BufferedReader in = new BufferedReader(getInputStreamFromWeatherEndpoint());
            String inputLine;
            content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(content.toString(), WeatherParsedResult.class);
    }
}
