package weather;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import weather.client.SQSClient;
import weather.client.WeatherClient;
import weather.conditions.WeatherConditions;
import weather.conditions.impl.Snorkeling;
import weather.exceptions.ClientException;
import weather.pojos.HandlerResponse;
import weather.pojos.WeatherParsedResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Handler implements RequestHandler<Object, String> {
    Logger logger = Logger.getLogger(Handler.class.getName());
    Gson gson = new Gson();
    WeatherClient weatherClient = new WeatherClient();
    WeatherConditions snorkeling = new Snorkeling();

    public Handler(){}

    public Handler(WeatherClient weatherClient, WeatherConditions weatherConditions) {
        this.weatherClient = weatherClient;
        this.snorkeling = weatherConditions;
    }

    @Override
    public String handleRequest(Object event, Context context) {
        List<WeatherParsedResult.MetricsPerMeasurment> weatherData;
        try {
            weatherData = weatherClient.fetchWeatherData();
        } catch (ClientException e) {
            logger.severe("issue with weather client data: "+e.getMessage());
            notifyOnError(e.getMessage());
            return new HandlerResponse().toString();
        }

        logger.info("found: " + weatherData.size() + " results");
        if(weatherData.isEmpty()) {
            return new HandlerResponse().toString();
        }
        HandlerResponse handlerResponse = new HandlerResponse(snorkeling.analyzeMeasurements(weatherData));
        if (handlerResponse.getSnorkelingResults().canGo()) {
            logger.info("can go snorkeling!");
            notifyOnSuccess();
        }
        return handlerResponse.toString();
    }
    boolean notifyOnError(String message) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("fromProcess", "weatherLambda");
        attributes.put("to", "asaf@asafmaoz.com");
        SQSClient.getInstance()
                .postMessageToQueue(attributes,
                        "Issue with weather client data! message: "+message,
                        QueuesEnum.EMAIL_QUEUE);
        return true;
    }
    boolean notifyOnSuccess() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("fromProcess", "weatherLambda");
        attributes.put("to", "asaf@asafmaoz.com");
        SQSClient.getInstance()
                .postMessageToQueue(attributes,
                        "Weather conditions are great for snorkeling!",
                        QueuesEnum.EMAIL_QUEUE);
        return true;
    }
}