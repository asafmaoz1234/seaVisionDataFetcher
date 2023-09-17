package com.seavision.seavisiondatafetcher.clients;

import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.Instant;

@Component
public class WeatherDataFetcherClient {

    private final WebClient client;

    @Value("${weather-client.base-url}")
    protected String baseUrl;
    @Value("${weather-client.request-params}")
    protected String reqParams;
    @Value("${weather-client.request-source}")
    protected String reqSource;
    @Value("${weather-client.auth-key}")
    protected String authKey;

    public WeatherDataFetcherClient(WebClient.Builder builder) {
        this.client = builder.baseUrl(this.baseUrl).build();
    }

    public FetchedData fetchData(String latitude, String longitude) {
        return this.client.get().uri("?lat="+latitude+"&lng="+longitude+"&"+this.buildUri())
                .header("Authorization", this.authKey)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(FetchedData.class)
                .block();
    }

    private String buildUri() {
        return "params="+this.reqParams+
                "&source="+this.reqSource+
                "&start="+Instant.now().minus(Duration.ofHours(48)).getEpochSecond()+
                "&end=" + Instant.now().getEpochSecond();
    }


}
