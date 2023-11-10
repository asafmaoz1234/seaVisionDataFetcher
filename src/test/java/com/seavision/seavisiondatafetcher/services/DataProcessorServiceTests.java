package com.seavision.seavisiondatafetcher.services;

import com.seavision.seavisiondatafetcher.BaseTest;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DataProcessorServiceTests extends BaseTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private DataProcessorService dataProcessorService;

    @Captor
    private ArgumentCaptor<List<WeatherData>> weatherDataListCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.dataProcessorService = new DataProcessorService(weatherRepository);
    }

    @Test
    public void validFetchedData_saveAllCalled() throws IOException {
        FetchedData fetchedData = this.loadSampleFetchedData();
        this.dataProcessorService.processData(fetchedData);
        verify(this.weatherRepository, times(1)).saveAll(any());
    }

    @Test
    public void validFetchedData_validListToSaveAll() throws IOException {
        FetchedData fetchedData = this.loadSampleFetchedData();
        this.dataProcessorService.processData(fetchedData);

        verify(this.weatherRepository).saveAll(weatherDataListCaptor.capture());
        List<WeatherData> capturedWeatherDataList = weatherDataListCaptor.getValue();

        assertEquals(capturedWeatherDataList.size(), fetchedData.getHours().size());
        assertEquals(capturedWeatherDataList.get(0).getLatitude(), String.valueOf(fetchedData.getMeta().getLat()));
        assertEquals(capturedWeatherDataList.get(4).getWaveHeight(), fetchedData.getHours().get(4).getWaveHeight().getNoaa());
    }

    @Test
    public void emptyFetchedData_saveAllNotCalled() {
        FetchedData fetchedData = new FetchedData();
        this.dataProcessorService.processData(fetchedData);
        verify(this.weatherRepository, Mockito.never()).saveAll(weatherDataListCaptor.capture());
    }

    @Test
    public void nullFetchedData_saveAllNotCalled() {
        this.dataProcessorService.processData(null);
        verify(this.weatherRepository, Mockito.never()).saveAll(weatherDataListCaptor.capture());
    }
}
