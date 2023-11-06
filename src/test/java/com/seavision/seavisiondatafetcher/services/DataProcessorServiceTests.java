package com.seavision.seavisiondatafetcher.services;

import com.seavision.seavisiondatafetcher.BaseTest;
import com.seavision.seavisiondatafetcher.clients.WeatherDataFetcherClient;
import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import com.seavision.seavisiondatafetcher.entities.WeatherData;
import com.seavision.seavisiondatafetcher.repositories.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class DataProcessorServiceTests extends BaseTest {

    @Mock
    private WeatherDataFetcherClient weatherDataFetcherClient;

    @Spy
    private WeatherRepository weatherRepository;

    @InjectMocks
    @Spy
    private DataProcessorService dataProcessorService;

    @Captor
    private ArgumentCaptor<List<WeatherData>> weatherDataListCaptor;

    @BeforeEach
    public void setUp() {
        this.dataProcessorService = new DataProcessorService(weatherRepository, weatherDataFetcherClient);
        MockitoAnnotations.openMocks(this);
    }
//
//    @Test
//    public void validFetchedData_saveAllCalled() throws IOException {
//        FetchedData fetchedData = this.loadSampleFetchedData();
//        doReturn(fetchedData).when(this.dataProcessorService).fetchWeatherData(any(), any());
//        doReturn(fetchedData.getHours()).when(this.weatherRepository).saveAll(any());
//        this.dataProcessorService.processData("12.3", "432.2");
//
//        verify(this.weatherRepository).saveAll(weatherDataListCaptor.capture());
//        List<WeatherData> capturedWeatherDataList = weatherDataListCaptor.getValue();
//        assertEquals(capturedWeatherDataList.size(), fetchedData.getHours().size());
//    }
}
