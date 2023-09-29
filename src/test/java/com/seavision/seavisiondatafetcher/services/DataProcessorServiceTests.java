package com.seavision.seavisiondatafetcher.services;

import com.seavision.seavisiondatafetcher.BaseTest;
//import com.seavision.seavisiondatafetcher.clients.WeatherDataFetcherClient;
//import com.seavision.seavisiondatafetcher.repositories.WeatherRepository;
import com.seavision.seavisiondatafetcher.clients.WeatherDataFetcherClient;
import com.seavision.seavisiondatafetcher.repositories.WeatherRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

public class DataProcessorServiceTests extends BaseTest {

    @Mock
    private WeatherDataFetcherClient weatherDataFetcherClient;

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    @Spy
    private DataProcessorService dataProcessorService;

    @BeforeEach
    public void setUp() {
        this.dataProcessorService = new DataProcessorService(weatherRepository, weatherDataFetcherClient);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test() throws IOException {
        doReturn(this.loadSampleFetchedData()).when(this.dataProcessorService).fetchWeatherData(any(), any());
        this.dataProcessorService.processData("12.3", "432.2");
        // verify list sent to weatherRepository.saveAll() size and content
        // verify weatherRepository.saveAll() was called once

    }
}
