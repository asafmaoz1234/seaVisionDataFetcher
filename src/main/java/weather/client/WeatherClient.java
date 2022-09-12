package weather.client;

import com.google.gson.Gson;
import weather.Handler;
import weather.exceptions.ClientException;
import weather.pojos.WeatherParsedResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static weather.config.EnvParams.API_REQUEST_POINT_LAT;
import static weather.config.EnvParams.API_REQUEST_POINT_LNG;

public class WeatherClient {
    Gson gson = new Gson();
    Logger logger = Logger.getLogger(Handler.class.getName());
    private Map<String, List<WeatherParsedResult.MetricsPerMeasurment>> storedResponsePerPoint;//storedResponsePerPoint

    private List<WeatherParsedResult.MetricsPerMeasurment> getStoredResponsePerPoint() {
        if(storedResponsePerPoint != null && !storedResponsePerPoint.get(API_REQUEST_POINT_LAT+API_REQUEST_POINT_LNG).isEmpty()) {
            logger.info("using stored response");
            return storedResponsePerPoint.get(API_REQUEST_POINT_LAT+API_REQUEST_POINT_LNG);
        }
        return new ArrayList<>();
    }

    private void setStoredResponsePerPoint(List<WeatherParsedResult.MetricsPerMeasurment> data) {
        if(storedResponsePerPoint == null) {
            storedResponsePerPoint = new HashMap<>();
        }
        storedResponsePerPoint.put(API_REQUEST_POINT_LAT+API_REQUEST_POINT_LNG, data);
    }

    InputStreamReader getInputStreamFromWeatherEndpoint() throws IOException {
        return new InputStreamReader(WeatherConnection.getConnection().getInputStream());
    }

    public List<WeatherParsedResult.MetricsPerMeasurment> fetchWeatherData() throws ClientException {
        if(!getStoredResponsePerPoint().isEmpty()) {
            return getStoredResponsePerPoint();
        }
        StringBuilder content;
        try {
            BufferedReader in = new BufferedReader(getInputStreamFromWeatherEndpoint());
            String inputLine;
            content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        } catch (Exception e) {
            logger.severe("could not complete request for weather data " + e.getMessage());
            throw new ClientException("error message: " + e.getMessage());
        }
        WeatherParsedResult weatherParsedResult = gson.fromJson(content.toString(), WeatherParsedResult.class);
        if(weatherParsedResult == null || weatherParsedResult.getHours() == null) {
            return new ArrayList<>();
        }
        this.setStoredResponsePerPoint(weatherParsedResult.getHours());
        return weatherParsedResult.getHours();
    }
}
