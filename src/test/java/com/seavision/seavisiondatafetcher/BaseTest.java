package com.seavision.seavisiondatafetcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BaseTest {

    @Value("${weather-client.base-url}")
    protected String baseUrl;

    @Value("${weather-client.auth-key}")
    protected String authKey;

    @Value("${weather-client.request-params}")
    protected String reqParams;

    @Test
    public void allPropertiesLoaded() {
        assertEquals("https://asafmaoz.com/404", this.baseUrl);
        assertEquals("stub-auth-key", this.authKey);
        assertEquals("stub-req-params", this.reqParams);
    }
}
