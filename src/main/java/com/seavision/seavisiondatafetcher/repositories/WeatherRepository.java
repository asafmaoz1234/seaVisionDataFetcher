package com.seavision.seavisiondatafetcher.repositories;

import com.seavision.seavisiondatafetcher.entities.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherData, Long> {
    List<WeatherData> findByLocationId(Long locationId);
    List<WeatherData> findAllByLocationIdAndMetricsDay(Long locationId, String metricDay);
}
