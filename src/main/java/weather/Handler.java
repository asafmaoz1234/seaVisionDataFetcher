package weather;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import weather.client.SQSClient;
import weather.client.WeatherClient;
import weather.conditions.WeatherConditions;
import weather.conditions.impl.Snorkeling;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Handler implements RequestHandler<Object, String> {
    Logger logger = Logger.getLogger(Handler.class.getName());
    WeatherClient weatherClient = new WeatherClient();
    WeatherConditions snorkeling = new Snorkeling();

    public Handler() {
    }

    public Handler(WeatherClient weatherClient, WeatherConditions weatherConditions) {
        this.weatherClient = weatherClient;
        this.snorkeling = weatherConditions;
    }

    @Override
    public String handleRequest(Object event, Context context) {
        String response = "200 OK";
        WeatherParsedResult weatherData = weatherClient.fetchWeatherData();
        logger.info("found: " + weatherData.getHours().size() + " results");
        if (snorkeling.canGo(weatherData.getHours())) {
            logger.info("can go snorkeling!");
            notifyOnSuccess();
        }
        return response;
    }

    boolean notifyOnSuccess() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("fromProcess", "weatherLambda");
        attributes.put("to", "asaf@asafmaoz.com");
        logger.info("posting message");
        SQSClient.getInstance()
                .postMessageToQueue(attributes,
                        "Weather conditions are great for snorkeling!",
                        QueuesEnum.EMAIL_QUEUE);
        return true;
    }
}