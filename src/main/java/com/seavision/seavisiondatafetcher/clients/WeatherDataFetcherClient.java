package com.seavision.seavisiondatafetcher.clients;

import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
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

    public WeatherDataFetcherClient() {
        this.client = WebClient.create();
    }

    public Mono<FetchedData> fetchData(String latitude, String longitude) {
        return this.client.get()
                .uri(this.buildUri(latitude, longitude))
                .header(HttpHeaders.AUTHORIZATION, this.authKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.isSameCodeAs(HttpStatus.PAYMENT_REQUIRED), this::handleClientUsageExceededError)
                .onStatus(httpStatusCode -> httpStatusCode.isSameCodeAs(HttpStatus.FORBIDDEN), this::handleClientAuthError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handleServerError)
                .bodyToMono(FetchedData.class);
    }

    URI buildUri(String latitude, String longitude) {
        return UriComponentsBuilder
                .fromUriString(this.baseUrl)
                .queryParam("lat", latitude)
                .queryParam("lng", longitude)
                .queryParam("params", this.reqParams)
                .queryParam("source", this.reqSource)
                .queryParam("start",Instant.now().minus(Duration.ofHours(24)).getEpochSecond())
                .queryParam("end", Instant.now().plus(Duration.ofHours(24)).getEpochSecond())
                .build()
                .toUri();
    }

    private Mono<? extends Throwable> handleClientAuthError(ClientResponse clientResponse) {
        // Handle client errors (e.g., 404 Not Found) here
        return Mono.error(new Exception("client error - auth failure"));
    }

    private Mono<? extends Throwable> handleClientUsageExceededError(ClientResponse clientResponse) {
        // Handle client errors (e.g., 404 Not Found) here
        return Mono.error(new Exception("client error - usage exceeded"));
    }

    private Mono<? extends Throwable> handleServerError(ClientResponse clientResponse) {
        // Handle server errors (e.g., 500 Internal Server Error) here
        return Mono.error(new RuntimeException("Server error"));
    }

}
