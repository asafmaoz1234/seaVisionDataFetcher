package weather.client;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import weather.enums.QueuesEnum;

import java.util.HashMap;
import java.util.Map;

import static weather.config.EnvParams.AWS_KEY;
import static weather.config.EnvParams.AWS_SECRET;

public class SQSClient {
    private static SQSClient sqsClient = null;
    AmazonSQS sqs;

    private SQSClient() {
        AWSCredentials credentials = new BasicAWSCredentials(
                AWS_KEY,
                AWS_SECRET
        );
        this.sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_1)
                .build();
    }

    public static SQSClient getInstance() {
        if(sqsClient == null) {
            sqsClient = new SQSClient();
        }
        return sqsClient;
    }

    public void postMessageToQueue(Map<String, String> attributes, String messageBody, QueuesEnum queue) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        attributes.forEach((k, v) -> messageAttributes.put(k, new MessageAttributeValue()
                .withStringValue(v).withDataType("String")));

        SendMessageRequest sendMessage = new SendMessageRequest()
                .withQueueUrl(queue.getUrl())
                .withMessageBody(messageBody)
                .withMessageAttributes(messageAttributes);
        this.sqs.sendMessage(sendMessage);
    }
}
