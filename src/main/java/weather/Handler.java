package weather;

import com.amazonaws.auth.policy.actions.SQSActions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.google.gson.Gson;
import weather.client.SQSClient;
import weather.client.WeatherClient;
import weather.conditions.WeatherConditions;
import weather.conditions.impl.Snorkeling;

import java.util.HashMap;
import java.util.Map;

public class Handler implements RequestHandler<Object, String> {
    LambdaLogger logger;
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
        this.logger = context.getLogger();
        String response = "200 OK";
        WeatherParsedResult weatherData = weatherClient.fetchWeatherData();
        logger.log("found: " + weatherData.getHours().size() + " results");
        if (snorkeling.canGo(weatherData.getHours())) {
            logger.log("can go snorkeling!");
            notifyOnSuccess();
        }
        return response;
    }

    boolean notifyOnSuccess() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("fromProcess", "weatherLambda");
        attributes.put("to", "asaf@asafmaoz.com");
        logger.log("posting message");
        SQSClient.getInstance()
                .postMessageToQueue(attributes,
                        "Weather conditions are great for snorkeling!",
                        QueuesEnum.EMAIL_QUEUE);
        return true;
    }
}