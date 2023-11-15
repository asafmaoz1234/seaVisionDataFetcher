package com.seavision.seavisiondatafetcher.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.seavision.seavisiondatafetcher.BaseTest;
import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WeatherDataFetcherClientTest extends BaseTest {

    @Autowired
    WeatherDataFetcherClient weatherDataFetcherClient;

    private WireMockServer wireMockServer;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

//    @Test
//    public void validResponse_status200() {
//        Mono<FetchedData> fetchedData = weatherDataFetcherClient.fetchData("32.464527991740894", "34.88256075359062");
//        FetchedData data = fetchedData.block();
//        System.out.println(data);
//        assertThat(data.getMeta().getLat(), is(equalTo(32.464527991740894)));
//        assertThat(data.getMeta().getLng(), is(equalTo(34.88256075359062)));
//    }

    @Test
    public void validResponse_parsedToObject() throws IOException {
        FetchedData fetchedData = this.loadSampleFetchedData();
        stubFor(get(urlPathMatching("/seavisionpro/.*"))
        .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(this.loadSampleText())));
        Mono<FetchedData> result = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
        StepVerifier.create(result)
                .assertNext(response -> {
                    Assertions.assertEquals(fetchedData.getHours().size(), response.getHours().size());
                    Assertions.assertEquals(fetchedData.getHours().get(10).getWaveHeight().getNoaa(), response.getHours().get(10).getWaveHeight().getNoaa());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void status402_clientException() {
        stubFor(get(urlPathMatching("/seavisionpro/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.PAYMENT_REQUIRED.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("")));
        Mono<FetchedData> error = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
        StepVerifier
                .create(error)
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("client error - usage exceeded")
                ).verify();
    }

    @Test
    public void status403_clientException() {
        stubFor(get(urlPathMatching("/seavisionpro/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.FORBIDDEN.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("")));
        Mono<FetchedData> error = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
        StepVerifier
                .create(error)
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("client error - auth failure")
                ).verify();
    }

    @Test
    public void status500_runtimeException() {
        stubFor(get(urlPathMatching("/seavisionpro/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("")));
        Mono<FetchedData> error = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
        StepVerifier
                .create(error)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Server error")
                ).verify();
    }

    @Test
    public void buildUri_shouldConstructValidURI() {
        // Given latitude and longitude values
        String latitude = "32.578070";
        String longitude = "34.908739";

        // Build the expected URI manually
        URI expectedUri = UriComponentsBuilder
                .fromUriString(weatherDataFetcherClient.baseUrl)
                .queryParam("lat", latitude)
                .queryParam("lng", longitude)
                .queryParam("params", weatherDataFetcherClient.reqParams)
                .queryParam("source", weatherDataFetcherClient.reqSource)
                .queryParam("start", Instant.now().minus(Duration.ofHours(24)).getEpochSecond())
                .queryParam("end", Instant.now().plus(Duration.ofHours(24)).getEpochSecond())
                .build().toUri();

        URI uri = this.weatherDataFetcherClient.buildUri(latitude, longitude);
        Assertions.assertEquals(expectedUri.getHost(), uri.getHost());
        Assertions.assertEquals(expectedUri.getPort(), uri.getPort());
        Assertions.assertEquals(expectedUri.getPath(), uri.getPath());
        Assertions.assertTrue(uri.getQuery().contains(latitude));
        Assertions.assertTrue(uri.getQuery().contains(longitude));
        Assertions.assertTrue(uri.getQuery().contains("start"));
        Assertions.assertTrue(uri.getQuery().contains("end"));
    }
}
