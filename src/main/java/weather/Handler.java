package weather;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import weather.client.SQSClient;

import java.util.HashMap;
import java.util.Map;

public class Handler implements RequestHandler<Object, String> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String handleRequest(Object event, Context context)
    {
        LambdaLogger logger = context.getLogger();
        String response = "200 OK";
        // log execution details
//        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
//        logger.log("CONTEXT: " + gson.toJson(context));
//        // process event
//        logger.log("EVENT: " + gson.toJson(event));
//        logger.log("EVENT TYPE: " + event.getClass());
        Map<String, String> attributes = new HashMap<>();
        attributes.put("from", "email.from");
        attributes.put("to", "email.to");
        logger.log("posting message");
        new SQSClient(logger)
                .postMessageToQueue(attributes, "Weather conditions are great!", QueuesEnum.EMAIL_QUEUE);
        return response;
    }
}
