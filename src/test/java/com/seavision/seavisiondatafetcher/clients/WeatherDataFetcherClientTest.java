package com.seavision.seavisiondatafetcher.clients;

import com.seavision.seavisiondatafetcher.BaseTest;
import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class WeatherDataFetcherClientTest extends BaseTest {

    @Autowired
    WeatherDataFetcherClient weatherDataFetcherClient;

    @Test
    public void testit() {
        Mono<FetchedData> fetchedData = weatherDataFetcherClient.fetchData("32.578070", "34.908739");
        System.out.println(fetchedData.block());
    }

}
