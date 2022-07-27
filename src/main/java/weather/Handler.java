package weather;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import weather.client.SQSClient;
import weather.client.WeatherClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Handler implements RequestHandler<Object, String> {
    LambdaLogger logger;
    WeatherClient weatherClient = new WeatherClient();

    @Override
    public String handleRequest(Object event, Context context) {
        this.logger = context.getLogger();
        String response = "200 OK";
        try {
            List<WeatherParsedResult> weatherData = weatherClient.fetchWeatherData();
            System.out.println(weatherData);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

        // log execution details
//        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
//        logger.log("CONTEXT: " + gson.toJson(context));
//        // process event
//        logger.log("EVENT: " + gson.toJson(event));
//        logger.log("EVENT TYPE: " + event.getClass());

        return response;
    }

    private void notifyOnSuccess() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("fromProcess", "weatherLambda");
        attributes.put("to", "asaf@asafmaoz.com");
        logger.log("posting message");
        new SQSClient()
                .postMessageToQueue(attributes, "Weather conditions are great!", QueuesEnum.EMAIL_QUEUE);
    }
}