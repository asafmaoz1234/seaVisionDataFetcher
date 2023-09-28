package com.seavision.seavisiondatafetcher.repositories;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "weather_data")
public class WeatherData{
    @Id
    private Long id;
    @Column(name = "longitude", nullable = false)
    private String longitude;
    @Column(name = "latitude", nullable = false)
    private String latitude;
    @Column(name = "metrics_day", nullable = false)
    private String metricsDay;

    public WeatherData() {
        super();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getMetricsDay() {
        return metricsDay;
    }

    public void setMetricsDay(String day) {
        this.metricsDay = day;
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
