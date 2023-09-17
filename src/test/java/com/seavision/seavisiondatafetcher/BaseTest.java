package com.seavision.seavisiondatafetcher;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BaseTest {

    @Value("${weather-client.base-url}")
    protected String baseUrl;

    @Value("${weather-client.auth-key}")
    protected String authKey;

    @Value("${weather-client.request-params}")
    protected String reqParams;

}
