package weather.client;

import com.google.gson.Gson;
import weather.Handler;
import weather.pojos.WeatherParsedResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WeatherClient {
    Gson gson = new Gson();
    Logger logger = Logger.getLogger(Handler.class.getName());
    private List<WeatherParsedResult.MetricsPerMeasurment> storedResponse;

    private List<WeatherParsedResult.MetricsPerMeasurment> getStoredResponse() {
        if(storedResponse != null) {
            logger.info("using stored response");
            return storedResponse;
        }
        return new ArrayList<>();
    }

    InputStreamReader getInputStreamFromWeatherEndpoint() throws IOException {
        return new InputStreamReader(WeatherConnection.getConnection().getInputStream());
    }

    public List<WeatherParsedResult.MetricsPerMeasurment> fetchWeatherData() {
        if(!getStoredResponse().isEmpty()) {
            return storedResponse;
        }
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
        WeatherParsedResult weatherParsedResult = gson.fromJson(content.toString(), WeatherParsedResult.class);
        if(weatherParsedResult == null || weatherParsedResult.getHours() == null) {
            return new ArrayList<>();
        }
        this.storedResponse = weatherParsedResult.getHours();
        return weatherParsedResult.getHours();
    }
}
