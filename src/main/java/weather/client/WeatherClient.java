package weather.client;

import com.google.gson.Gson;
import weather.Handler;
import weather.WeatherParsedResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class WeatherClient {
    Gson gson = new Gson();
    Logger logger = Logger.getLogger(Handler.class.getName());

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
            logger.severe("could not complete request for weather data " + e.getMessage());
            throw new RuntimeException(e);
        }
        return gson.fromJson(content.toString(), WeatherParsedResult.class);
    }
}
