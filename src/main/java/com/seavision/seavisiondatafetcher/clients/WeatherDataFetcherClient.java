package com.seavision.seavisiondatafetcher.clients;

import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
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
        this.client = builder.baseUrl(this.baseUrl)
                .build();
    }

    public Mono<FetchedData> fetchData(String latitude, String longitude) {
        return this.client.get()
                .uri(this.buildUri(latitude, longitude))
                .header(HttpHeaders.AUTHORIZATION, this.authKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(FetchedData.class);
    }

    private URI buildUri(String latitude, String longitude) {
        return UriComponentsBuilder
                .fromUriString(this.baseUrl)
                .queryParam("lat", latitude)
                .queryParam("lng", longitude)
                .queryParam("params", this.reqParams)
                .queryParam("source", this.reqSource)
                .queryParam("start",Instant.now().minus(Duration.ofHours(48)).getEpochSecond())
                .queryParam("end", Instant.now().getEpochSecond())
                .build()
                .toUri();
    }


}
