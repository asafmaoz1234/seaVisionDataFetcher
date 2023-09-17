package com.seavision.seavisiondatafetcher.dtos;

import java.util.List;

public class FetchedData {
    private List<MetricsPerMeasurement> hours;

    public List<MetricsPerMeasurement> getHours() {
        return hours;
    }

    public void setHours(List<MetricsPerMeasurement> hours) {
        this.hours = hours;
    }

    public static class MetricsPerMeasurement {
        private String time;
        private WeatherParamData waveHeight;

        public MetricsPerMeasurement(String time, WeatherParamData waveHeight) {
            this.time = time;
            this.waveHeight = waveHeight;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public WeatherParamData getWaveHeight() {
            return waveHeight;
        }

        public void setWaveHeight(WeatherParamData waveHeight) {
            this.waveHeight = waveHeight;
        }
    }

    public static class WeatherParamData {
        private Double noaa;

        public WeatherParamData(Double noaa) {
            this.noaa = noaa;
        }

        public Double getNoaa() {
            return noaa;
        }

        public void setNoaa(Double noaa) {
            this.noaa = noaa;
        }
    }
}
