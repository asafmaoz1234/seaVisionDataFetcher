package com.seavision.seavisiondatafetcher.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "weather_data")
public class WeatherData{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "longitude", nullable = false)
    private String longitude;
    @Column(name = "latitude", nullable = false)
    private String latitude;
    @Column(name = "metrics_day", nullable = false)
    private String metricsDay;
    @Column(name = "wave_height", nullable = false)
    private Double waveHeight;

    public WeatherData() {
        super();
    }

    public WeatherData setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public WeatherData setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
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

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getMetricsDay() {
        return metricsDay;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", metricsDay='" + metricsDay + '\'' +
                '}';
    }

}
