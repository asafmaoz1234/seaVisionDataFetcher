package com.seavision.seavisiondatafetcher.repositories;

import com.seavision.seavisiondatafetcher.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class WeatherDataTests extends BaseTest {

    @Autowired
    WeatherRepository weatherRepository;

    @Test
    public void newLongAndLat_emptyResponse() {
        List<WeatherData> response = weatherRepository.findByLongitudeAndLatitude(Math.random()+"", Math.random()+"");
        assertEquals(response.size(),0);
    }
    @Test
    public void insertNewRecord_responseSizeOne() {
        String lat = Math.random()+"";
        String lng = Math.random()+"";
        String metricDaya = "21/09/2023";
        weatherRepository.save(new WeatherData()
                .setMetricsDay(metricDaya)
                .setLatitude(lat)
                .setLongitude(lng));
        List<WeatherData> response = weatherRepository.findAllByLongitudeAndLatitudeAndMetricsDay(lng,lat, metricDaya);
        assertThat(response.size(), is(equalTo(1)));
        assertThat(response.get(0).getLatitude(), is(equalTo(lat)));
        assertThat(response.get(0).getLongitude(), is(equalTo(lng)));
        assertThat(response.get(0).getMetricsDay(), is(equalTo(metricDaya)));
    }
}