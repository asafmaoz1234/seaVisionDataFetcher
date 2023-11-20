package com.seavision.seavisiondatafetcher.repositories;

import com.seavision.seavisiondatafetcher.BaseTest;
import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import com.seavision.seavisiondatafetcher.entities.WeatherData;
import com.seavision.seavisiondatafetcher.pojos.GeneralUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherDataTests extends BaseTest {

    @Autowired
    WeatherRepository weatherRepository;

    @BeforeEach
    public void setUp() {
        weatherRepository.deleteAll();
    }
    @Test
    public void newLongAndLat_emptyResponse() {
        List<WeatherData> response = weatherRepository.findByLocationId(1L);
        assertEquals(response.size(),0);
    }
    @Test
    public void insertNewRecord_responseSizeOne() {
        String lat = Math.random()+"";
        String lng = Math.random()+"";
        String metricDay = "21/09/2023-06";
        weatherRepository.save(new WeatherData()
                .setMetricsDay(metricDay)
                .setWaveHeight(6.5)
                .setLocationId(1L));
        List<WeatherData> response = weatherRepository.findAllByLocationIdAndMetricsDay(1L, metricDay);
        assertThat(response.size(), is(equalTo(1)));
        assertThat(response.get(0).getMetricsDay(), is(equalTo(metricDay)));
        assertThat(response.get(0).getWaveHeight(), is(equalTo(6.5)));
        assertThat(response.get(0).getLocationId(), is(equalTo(1L)));
    }

    @Test
    public void listWeatherResults_allSaved() throws IOException {
        FetchedData data = this.loadSampleFetchedData();
        List<WeatherData> toSave = data.getHours().stream()
                .map(dataPerHour -> new WeatherData()
                        .setMetricsDay(GeneralUtilities.convertToDDMMYYYY(dataPerHour.getTime()))
                        .setWaveHeight(dataPerHour.getWaveHeight().getNoaa())
                        .setLocationId(1L))
                .collect(Collectors.toList());
        weatherRepository.saveAll(toSave);
        List<WeatherData> response = weatherRepository.findByLocationId(1L);
        assertThat(response.size(), is(equalTo(73)));
    }
}
