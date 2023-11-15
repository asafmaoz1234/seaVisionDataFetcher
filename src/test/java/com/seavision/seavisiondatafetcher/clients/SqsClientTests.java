package com.seavision.seavisiondatafetcher.clients;

import com.seavision.seavisiondatafetcher.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SqsClientTests extends BaseTest {

    @Autowired
    SqsClient sqsClient;

    @Test
    public void publishMessageTest() {
        sqsClient.publishMessage(null, "test");

    }
}
