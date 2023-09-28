package com.seavision.seavisiondatafetcher.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherData, Long> {
    List<WeatherData> findByLongitudeAndLatitude(String longitude, String latitude);
}
