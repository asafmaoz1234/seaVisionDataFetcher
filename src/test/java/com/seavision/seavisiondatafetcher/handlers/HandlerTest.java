package com.seavision.seavisiondatafetcher.handlers;

import com.seavision.seavisiondatafetcher.BaseTest;
import com.seavision.seavisiondatafetcher.clients.WeatherDataFetcherClient;
import com.seavision.seavisiondatafetcher.exceptions.DbException;
import com.seavision.seavisiondatafetcher.repositories.LocationsRepository;
import com.seavision.seavisiondatafetcher.services.DataProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class HandlerTest extends BaseTest {


    private Handler handler;

    @Mock
    private DataProcessorService dataProcessorService;

    @Mock
    private LocationsRepository locationsRepository;

    @Mock
    private WeatherDataFetcherClient weatherDataFetcherClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new Handler(dataProcessorService, locationsRepository, weatherDataFetcherClient);
    }

    @Test
    public void emptyLocations_doNothing() throws DbException {
        doReturn(Collections.emptyList()).when(locationsRepository).findAll();
        String response = handler.handleRequest();
        assertEquals("Empty locations", response);
        verify(locationsRepository).findAll();
        verify(dataProcessorService, never()).processData(any());
    }

    @Test
    public void dbException_ExceptionThrown() {
        when(locationsRepository.findAll()).thenThrow(new RuntimeException());
        assertThrows(DbException.class, () -> handler.handleRequest());
        verify(dataProcessorService, never()).processData(any());
    }


}
