package com.seavision.seavisiondatafetcher.clients;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SqsClient {

    private final String queueUrl;
    private final AmazonSQS sqsClient;


    public SqsClient(@Value("${aws.sqs.account}") String account, @Value("${aws.sqs.region}") String region, @Value("${aws.sqs.queue-name}") String queueName) {
        this.sqsClient = AmazonSQSClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .build();
        this.queueUrl = "https://sqs." + region + ".amazonaws.com/" + account + "/" + queueName;
    }

    public void publishMessage(Map<String, String> attributes, String messageBody) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        if(attributes == null)
            attributes = new HashMap<>();
        attributes.forEach((k, v) -> messageAttributes.put(k, new MessageAttributeValue()
                .withStringValue(v).withDataType("String")));

        SendMessageRequest sendMessage = new SendMessageRequest()
                .withQueueUrl(this.queueUrl)
                .withMessageBody(messageBody)
                .withMessageAttributes(messageAttributes);
        this.sqsClient.sendMessage(sendMessage);
    }
}
