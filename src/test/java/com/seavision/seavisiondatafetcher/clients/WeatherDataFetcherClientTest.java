package com.seavision.seavisiondatafetcher.clients;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.seavision.seavisiondatafetcher.BaseTest;
import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
public class WeatherDataFetcherClientTest extends BaseTest {

    @Autowired
    WeatherDataFetcherClient weatherDataFetcherClient;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    @Test
    public void validResponse_parsedToObject() {
        stubFor(get(urlPathMatching("/stubit/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"hours\":[{\"time\":\"2022-09-10T05:00:00+00:00\",\"waveHeight\":{\"noaa\":0.61}},{\"time\":\"2022-09-10T06:00:00+00:00\",\"waveHeight\":{\"noaa\":0.6}},{\"time\":\"2022-09-10T07:00:00+00:00\",\"waveHeight\":{\"noaa\":0.59}},{\"time\":\"2022-09-10T08:00:00+00:00\",\"waveHeight\":{\"noaa\":0.58}},{\"time\":\"2022-09-10T09:00:00+00:00\",\"waveHeight\":{\"noaa\":0.57}},{\"time\":\"2022-09-10T10:00:00+00:00\",\"waveHeight\":{\"noaa\":0.58}},{\"time\":\"2022-09-10T11:00:00+00:00\",\"waveHeight\":{\"noaa\":0.58}},{\"time\":\"2022-09-10T12:00:00+00:00\",\"waveHeight\":{\"noaa\":0.59}},{\"time\":\"2022-09-10T13:00:00+00:00\",\"waveHeight\":{\"noaa\":0.6}},{\"time\":\"2022-09-10T14:00:00+00:00\",\"waveHeight\":{\"noaa\":0.62}},{\"time\":\"2022-09-10T15:00:00+00:00\",\"waveHeight\":{\"noaa\":0.63}},{\"time\":\"2022-09-10T16:00:00+00:00\",\"waveHeight\":{\"noaa\":0.63}},{\"time\":\"2022-09-10T17:00:00+00:00\",\"waveHeight\":{\"noaa\":0.63}},{\"time\":\"2022-09-10T18:00:00+00:00\",\"waveHeight\":{\"noaa\":0.63}},{\"time\":\"2022-09-10T19:00:00+00:00\",\"waveHeight\":{\"noaa\":0.62}},{\"time\":\"2022-09-10T20:00:00+00:00\",\"waveHeight\":{\"noaa\":0.62}},{\"time\":\"2022-09-10T21:00:00+00:00\",\"waveHeight\":{\"noaa\":0.61}},{\"time\":\"2022-09-10T22:00:00+00:00\",\"waveHeight\":{\"noaa\":0.6}},{\"time\":\"2022-09-10T23:00:00+00:00\",\"waveHeight\":{\"noaa\":0.6}},{\"time\":\"2022-09-11T00:00:00+00:00\",\"waveHeight\":{\"noaa\":0.59}},{\"time\":\"2022-09-11T01:00:00+00:00\",\"waveHeight\":{\"noaa\":0.59}},{\"time\":\"2022-09-11T02:00:00+00:00\",\"waveHeight\":{\"noaa\":0.58}},{\"time\":\"2022-09-11T03:00:00+00:00\",\"waveHeight\":{\"noaa\":0.58}},{\"time\":\"2022-09-11T04:00:00+00:00\",\"waveHeight\":{\"noaa\":0.57}},{\"time\":\"2022-09-11T05:00:00+00:00\",\"waveHeight\":{\"noaa\":0.56}},{\"time\":\"2022-09-11T06:00:00+00:00\",\"waveHeight\":{\"noaa\":0.55}},{\"time\":\"2022-09-11T07:00:00+00:00\",\"waveHeight\":{\"noaa\":0.54}},{\"time\":\"2022-09-11T08:00:00+00:00\",\"waveHeight\":{\"noaa\":0.52}},{\"time\":\"2022-09-11T09:00:00+00:00\",\"waveHeight\":{\"noaa\":0.51}},{\"time\":\"2022-09-11T10:00:00+00:00\",\"waveHeight\":{\"noaa\":0.51}},{\"time\":\"2022-09-11T11:00:00+00:00\",\"waveHeight\":{\"noaa\":0.5}},{\"time\":\"2022-09-11T12:00:00+00:00\",\"waveHeight\":{\"noaa\":0.5}},{\"time\":\"2022-09-11T13:00:00+00:00\",\"waveHeight\":{\"noaa\":0.5}},{\"time\":\"2022-09-11T14:00:00+00:00\",\"waveHeight\":{\"noaa\":0.5}},{\"time\":\"2022-09-11T15:00:00+00:00\",\"waveHeight\":{\"noaa\":0.5}},{\"time\":\"2022-09-11T16:00:00+00:00\",\"waveHeight\":{\"noaa\":0.49}},{\"time\":\"2022-09-11T17:00:00+00:00\",\"waveHeight\":{\"noaa\":0.49}},{\"time\":\"2022-09-11T18:00:00+00:00\",\"waveHeight\":{\"noaa\":0.48}},{\"time\":\"2022-09-11T19:00:00+00:00\",\"waveHeight\":{\"noaa\":0.47}},{\"time\":\"2022-09-11T20:00:00+00:00\",\"waveHeight\":{\"noaa\":0.46}},{\"time\":\"2022-09-11T21:00:00+00:00\",\"waveHeight\":{\"noaa\":0.45}},{\"time\":\"2022-09-11T22:00:00+00:00\",\"waveHeight\":{\"noaa\":0.43}},{\"time\":\"2022-09-11T23:00:00+00:00\",\"waveHeight\":{\"noaa\":0.42}},{\"time\":\"2022-09-12T00:00:00+00:00\",\"waveHeight\":{\"noaa\":0.4}},{\"time\":\"2022-09-12T01:00:00+00:00\",\"waveHeight\":{\"noaa\":0.39}},{\"time\":\"2022-09-12T02:00:00+00:00\",\"waveHeight\":{\"noaa\":0.38}},{\"time\":\"2022-09-12T03:00:00+00:00\",\"waveHeight\":{\"noaa\":0.37}},{\"time\":\"2022-09-12T04:00:00+00:00\",\"waveHeight\":{\"noaa\":0.37}},{\"time\":\"2022-09-12T05:00:00+00:00\",\"waveHeight\":{\"noaa\":0.36}},{\"time\":\"2022-09-12T06:00:00+00:00\",\"waveHeight\":{\"noaa\":0.36}},{\"time\":\"2022-09-12T07:00:00+00:00\",\"waveHeight\":{\"noaa\":0.36}},{\"time\":\"2022-09-12T08:00:00+00:00\",\"waveHeight\":{\"noaa\":0.37}},{\"time\":\"2022-09-12T09:00:00+00:00\",\"waveHeight\":{\"noaa\":0.37}},{\"time\":\"2022-09-12T10:00:00+00:00\",\"waveHeight\":{\"noaa\":0.38}},{\"time\":\"2022-09-12T11:00:00+00:00\",\"waveHeight\":{\"noaa\":0.39}},{\"time\":\"2022-09-12T12:00:00+00:00\",\"waveHeight\":{\"noaa\":0.4}},{\"time\":\"2022-09-12T13:00:00+00:00\",\"waveHeight\":{\"noaa\":0.4}},{\"time\":\"2022-09-12T14:00:00+00:00\",\"waveHeight\":{\"noaa\":0.41}},{\"time\":\"2022-09-12T15:00:00+00:00\",\"waveHeight\":{\"noaa\":0.41}},{\"time\":\"2022-09-12T16:00:00+00:00\",\"waveHeight\":{\"noaa\":0.41}},{\"time\":\"2022-09-12T17:00:00+00:00\",\"waveHeight\":{\"noaa\":0.42}},{\"time\":\"2022-09-12T18:00:00+00:00\",\"waveHeight\":{\"noaa\":0.42}},{\"time\":\"2022-09-12T19:00:00+00:00\",\"waveHeight\":{\"noaa\":0.43}},{\"time\":\"2022-09-12T20:00:00+00:00\",\"waveHeight\":{\"noaa\":0.43}},{\"time\":\"2022-09-12T21:00:00+00:00\",\"waveHeight\":{\"noaa\":0.44}},{\"time\":\"2022-09-12T22:00:00+00:00\",\"waveHeight\":{\"noaa\":0.46}},{\"time\":\"2022-09-12T23:00:00+00:00\",\"waveHeight\":{\"noaa\":0.49}},{\"time\":\"2022-09-13T00:00:00+00:00\",\"waveHeight\":{\"noaa\":0.51}},{\"time\":\"2022-09-13T01:00:00+00:00\",\"waveHeight\":{\"noaa\":0.53}},{\"time\":\"2022-09-13T02:00:00+00:00\",\"waveHeight\":{\"noaa\":0.54}},{\"time\":\"2022-09-13T03:00:00+00:00\",\"waveHeight\":{\"noaa\":0.56}},{\"time\":\"2022-09-13T04:00:00+00:00\",\"waveHeight\":{\"noaa\":0.57}},{\"time\":\"2022-09-13T05:00:00+00:00\",\"waveHeight\":{\"noaa\":0.58}}],\"meta\":{\"cost\":1,\"dailyQuota\":10,\"end\":\"2022-09-1305:47\",\"lat\":32.57807,\"lng\":34.908739,\"params\":[\"waveHeight\"],\"requestCount\":1,\"source\":[\"noaa\"],\"start\":\"2022-09-1005:00\"}}")));
        Mono<FetchedData> fetchedData = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
        FetchedData fetchedDataGet = fetchedData.block();
        assert fetchedDataGet != null;
        assertThat(fetchedDataGet.getHours().size(), is(equalTo(73)));
        assertThat(fetchedDataGet.getHours().get(10).getWaveHeight().getNoaa(), is(equalTo(0.63)));
    }

    @Test(expected = Exception.class)
    public void status402_clientException() {
        stubFor(get(urlPathMatching("/stubit/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.PAYMENT_REQUIRED.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("")));
        Mono<FetchedData> fetchedData = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
        fetchedData.block();
    }

    @Test(expected = Exception.class)
    public void status403_clientException() {
        stubFor(get(urlPathMatching("/stubit/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.FORBIDDEN.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("")));
        Mono<FetchedData> fetchedData = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
        fetchedData.block();
    }

    @Test(expected = RuntimeException.class)
    public void status500_runtimeException() {
        stubFor(get(urlPathMatching("/stubit/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("")));
        Mono<FetchedData> fetchedData = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
        fetchedData.block();
    }

}
