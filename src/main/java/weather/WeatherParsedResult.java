package weather;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherParsedResult {
    private List<MetricsPerMeasurment> hours;

    public List<MetricsPerMeasurment> getHours() {
        return hours;
    }

    public void setHours(List<MetricsPerMeasurment> hours) {
        this.hours = hours;
    }

    public static class MetricsPerMeasurment {
        private String time;
        private WeatherParamData waveHeight;

        public MetricsPerMeasurment(String time, WeatherParamData waveHeight) {
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



