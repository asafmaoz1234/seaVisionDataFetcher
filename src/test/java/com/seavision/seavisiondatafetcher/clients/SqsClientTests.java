package com.seavision.seavisiondatafetcher.clients;

import com.amazonaws.services.sqs.model.Message;
import com.seavision.seavisiondatafetcher.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqsClientTests extends BaseTest {

    @Autowired
    SqsClient sqsClient;

    @Value("${aws.sqs.queue-name}")
    private String queueName;

    private static boolean isInitialized = false;

    /**
     * generate a queue for testing
     */
    @BeforeEach
    public void setup() {
        if (!isInitialized) {
            this.sqsClient.createQueue(this.queueName);
            isInitialized = true;
        }
        // Your 'before each' code here
    }

    @Test
    public void publishMessage_messageInQueue() {
        sqsClient.publishMessage(null, "test");
        List<Message> inqueue = this.sqsClient.getMessages();
        assertEquals(1, inqueue.size());
    }
}

