package com.seavision.seavisiondatafetcher.handlers;

import com.seavision.seavisiondatafetcher.BaseTest;
import com.seavision.seavisiondatafetcher.clients.SqsClient;
import com.seavision.seavisiondatafetcher.clients.WeatherDataFetcherClient;
import com.seavision.seavisiondatafetcher.entities.Locations;
import com.seavision.seavisiondatafetcher.exceptions.DbException;
import com.seavision.seavisiondatafetcher.repositories.LocationsRepository;
import com.seavision.seavisiondatafetcher.services.DataProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class HandlerTest extends BaseTest {

//    @Autowired
    private Handler handler;

    @Mock
    private DataProcessorService dataProcessorService;

    @Mock
    private LocationsRepository locationsRepository;

    @Mock
    private WeatherDataFetcherClient weatherDataFetcherClient;

    @Mock
    private SqsClient sqsClient;

    List<Locations> locations = Arrays.asList(
            new Locations().setLocationName("north")
                    .setLatitude("53.3498")
                    .setLongitude("6.2603")
                    .setId(1L),
            new Locations().setLocationName("south")
                    .setLatitude("52.3498")
                    .setLongitude("5.2603")
                    .setId(2L));

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new Handler(dataProcessorService, locationsRepository, weatherDataFetcherClient, sqsClient);
    }

//    @Test
//    public void fullFlow_success() throws DbException {
//        this.handler.handleRequest();
//    }

    @Test
    public void emptyLocations_doNothing() throws DbException {
        doReturn(Collections.emptyList()).when(locationsRepository).findAllByIsActiveIsTrue();
        String response = handler.handleRequest();
        assertEquals("Empty locations", response);
        verify(locationsRepository).findAllByIsActiveIsTrue();
        verify(dataProcessorService, never()).processData(any(), any());
        verify(sqsClient,times(1)).publishMessage(null, "Empty locations");
    }

    @Test
    public void dbException_ExceptionThrown() {
        when(locationsRepository.findAllByIsActiveIsTrue()).thenThrow(new RuntimeException());
        assertThrows(DbException.class, () -> handler.handleRequest());
        verify(dataProcessorService, never()).processData(any(), any());
        verify(sqsClient, times(1)).publishMessage(null, "DB ERROR");
    }

    @Test
    public void successfulRequest() throws DbException, IOException {
        when(locationsRepository.findAllByIsActiveIsTrue()).thenReturn(this.locations);
        when(weatherDataFetcherClient.fetchData(anyString(), anyString())).thenReturn(Mono.just(this.loadSampleFetchedData()));
        String response = handler.handleRequest();
        assertEquals("Successful Done", response);
        verify(locationsRepository).findAllByIsActiveIsTrue();
        verify(dataProcessorService, times(locations.size())).processData(any(), any());
        verify(sqsClient, times(1)).publishMessage(null, "Successful Done");
    }

    @Test
    public void successfulRequest_emptyFetched() throws DbException {
        when(locationsRepository.findAllByIsActiveIsTrue()).thenReturn(this.locations);
        when(weatherDataFetcherClient.fetchData(anyString(), anyString())).thenReturn(Mono.empty());
        String response = handler.handleRequest();
        assertEquals("Empty results Done", response);
        verify(dataProcessorService, never()).processData(any(), any());
        verify(sqsClient, times(1)).publishMessage(null, "Empty results");
    }

}
