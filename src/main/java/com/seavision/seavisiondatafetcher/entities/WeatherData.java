package com.seavision.seavisiondatafetcher.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "weather_data")
public class WeatherData{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "metrics_day", nullable = false)
    private String metricsDay;
    @Column(name = "wave_height", nullable = false)
    private Double waveHeight;
    @Column(name = "location_id", nullable = false)
    private Long locationId;

    public WeatherData() {
        super();
    }

    public WeatherData setMetricsDay(String metricsDay) {
        this.metricsDay = metricsDay;
        return this;
    }

    public Double getWaveHeight() {
        return waveHeight;
    }

    public WeatherData setWaveHeight(Double waveHeight) {
        this.waveHeight = waveHeight;
        return this;
    }

    public String getMetricsDay() {
        return metricsDay;
    }

    public Long getLocationId() {
        return locationId;
    }

    public WeatherData setLocationId(Long locationId) {
        this.locationId = locationId;
        return this;
    }
}
