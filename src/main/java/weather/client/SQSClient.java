package weather.client;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import weather.QueuesEnum;

import java.util.HashMap;
import java.util.Map;

public class SQSClient {
    LambdaLogger logger;
    AmazonSQS sqs;

    public SQSClient(LambdaLogger logger) {
        AWSCredentials credentials = new BasicAWSCredentials(
                System.getenv("aws_key"),
                System.getenv("aws_secret")
        );
        this.sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_1)
                .build();
        this.logger = logger;
    }

    public void postMessageToQueue(Map<String, String> attributes, String messageBody, QueuesEnum queue) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        attributes.forEach((k, v) -> messageAttributes.put(k, new MessageAttributeValue()
                .withStringValue(v).withDataType("String")));

        SendMessageRequest sendMessage = new SendMessageRequest()
                .withQueueUrl(queue.getUrl())
                .withMessageBody(messageBody)
                .withMessageAttributes(messageAttributes);
        try {
            logger.log("posting message: "+ sendMessage);
            this.sqs.sendMessage(sendMessage);
        } catch (Exception e) {
            logger.log("failed to post to queue: "+ e);
        }
    }
}
