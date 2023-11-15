package com.seavision.seavisiondatafetcher.clients;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SqsClient {

    private final String queueUrl;
    protected final AmazonSQS amazonSQS;


    public SqsClient(@Value("${aws.sqs.endpoint-url}") String endpointUrl, @Value("${aws.sqs.account-id}") String accountId, @Value("${aws.sqs.region}") String region, @Value("${aws.sqs.queue-name}") String queueName) {
        this.amazonSQS = AmazonSQSClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder
                        .EndpointConfiguration(endpointUrl, region))
                .build();
        this.queueUrl =  endpointUrl + "/" + accountId + "/" + queueName;
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
        this.amazonSQS.sendMessage(sendMessage);
    }

    public List<Message> getMessages() {
        ReceiveMessageResult receiveMessageResult = this.amazonSQS.receiveMessage(this.queueUrl);
        return receiveMessageResult.getMessages();
    }

    public String createQueue(String queueName) {
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);

        CreateQueueResult createQueueResult = this.amazonSQS.createQueue(createQueueRequest);
        return createQueueResult.getQueueUrl();
    }
}