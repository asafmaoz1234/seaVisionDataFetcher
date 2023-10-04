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
import reactor.core.publisher.Mono;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
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


    @Test
    public void validResponse_parsedToObject() throws IOException {
        stubFor(get(urlPathMatching("/stubit/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(this.loadSampleText())));
        Mono<FetchedData> fetchedData = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
        FetchedData fetchedDataGet = fetchedData.block();
        assert fetchedDataGet != null;
        assertThat(fetchedDataGet.getHours().size(), is(equalTo(73)));
        assertThat(fetchedDataGet.getHours().get(10).getWaveHeight().getNoaa(), is(equalTo(0.63)));
    }

    @Test
    public void status402_clientException() {
        stubFor(get(urlPathMatching("/stubit/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.PAYMENT_REQUIRED.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("")));
        Assertions.assertThrows(Exception.class, () -> {
            Mono<FetchedData> fetchedData = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
            fetchedData.block();
        });
    }

    @Test
    public void status403_clientException() {
        stubFor(get(urlPathMatching("/stubit/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.FORBIDDEN.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("")));
        Assertions.assertThrows(Exception.class, () -> {
            Mono<FetchedData> fetchedData = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
            fetchedData.block();
        });
    }

    @Test
    public void status500_runtimeException() {
        stubFor(get(urlPathMatching("/stubit/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("")));
        Assertions.assertThrows(RuntimeException.class, () -> {
            Mono<FetchedData> fetchedData = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
            fetchedData.block();
        });
    }
}
