package com.seavision.seavisiondatafetcher.services;

import com.seavision.seavisiondatafetcher.BaseTest;
import com.seavision.seavisiondatafetcher.entities.WeatherData;
import com.seavision.seavisiondatafetcher.repositories.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;

import java.util.List;

public class DataProcessorServiceTests extends BaseTest {


    @Spy
    private WeatherRepository weatherRepository;

    @InjectMocks
    @Spy
    private DataProcessorService dataProcessorService;

    @Captor
    private ArgumentCaptor<List<WeatherData>> weatherDataListCaptor;

    @BeforeEach
    public void setUp() {
        this.dataProcessorService = new DataProcessorService(weatherRepository);
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
