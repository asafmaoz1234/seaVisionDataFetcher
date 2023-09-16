package weather;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import weather.client.SQSClient;
import weather.client.WeatherClient;
import weather.enums.QueuesEnum;
import weather.exceptions.ClientException;
import weather.pojos.HandlerResponse;
import weather.pojos.WeatherParsedResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Handler implements RequestHandler<Object, String> {
    Logger logger = Logger.getLogger(Handler.class.getName());
    WeatherClient weatherClient = new WeatherClient();
    QueuesEnum queuesEnum = QueuesEnum.EMAIL_QUEUE;

    public Handler(){}

    public Handler(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    @Override
    public String handleRequest(Object event, Context context) {
        List<WeatherParsedResult.MetricsPerMeasurment> weatherData;
        try {
            weatherData = weatherClient.fetchWeatherData();
        } catch (ClientException e) {
            logger.severe("issue with weather client data: "+e.getMessage());
            queuesEnum.publishToQ("issue with weather client data: did not get any results " + e.getMessage(),
                    "asaf@asafmaoz.com",
                    "snorkeling.notifications");
            return new HandlerResponse().toString();
        }

        logger.info("found: " + weatherData.size() + " results");
        if(weatherData.isEmpty()) {
            logger.severe("issue with weather client data: did not get any results");
            queuesEnum.publishToQ("issue with weather client data: did not get any results",
                    "asaf@asafmaoz.com",
                    "snorkeling.notifications");
            return new HandlerResponse().toString();
        }

        HandlerResponse handlerResponse = new HandlerResponse(true);
        if (handlerResponse.isAllConditionsPassed()) {
            logger.info("can go snorkeling!");
            queuesEnum.publishToQ("Weather conditions are great for snorkeling!",
                    "asaf@asafmaoz.com",
                    "snorkeling.notifications");
        }
        return handlerResponse.toString();
    }

}