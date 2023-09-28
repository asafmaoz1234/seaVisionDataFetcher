package com.seavision.seavisiondatafetcher.services;

import com.seavision.seavisiondatafetcher.clients.WeatherDataFetcherClient;
import com.seavision.seavisiondatafetcher.dtos.DataPerHour;
import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import com.seavision.seavisiondatafetcher.repositories.WeatherData;
import com.seavision.seavisiondatafetcher.repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataProcessorService {

    final
    WeatherRepository weatherRepository;
    final
    WeatherDataFetcherClient weatherDataFetcherClient;

    public DataProcessorService(WeatherRepository weatherRepository, WeatherDataFetcherClient weatherDataFetcherClient) {
        this.weatherRepository = weatherRepository;
        this.weatherDataFetcherClient = weatherDataFetcherClient;
    }

    public void processData(String latitude, String longitude) {
        FetchedData fetchedData = this.fetchWeatherData(latitude, longitude);
        List<WeatherData> weatherDataList = this.extractWeatherData(fetchedData.getHours(), latitude, longitude);
        this.weatherRepository.saveAll(weatherDataList);
    }

    private List<WeatherData> extractWeatherData(List<DataPerHour> dataPerHourList, String latitude, String longitude) {
        String metricsDate = this.convertToDDMMYYYY(dataPerHourList.get(0).getTime());
        return dataPerHourList.stream()
                .map(dataPerHour -> new WeatherData()
                        .setLatitude(latitude)
                        .setLongitude(longitude)
                        .setMetricsDay(metricsDate)
                        .setWaveHeight(dataPerHour.getWaveHeight().getNoaa()))
                .collect(Collectors.toList());
    }

    private String convertToDDMMYYYY(String inputDate) {
        OffsetDateTime dateTime = OffsetDateTime.parse(inputDate);
        // Create a formatter to convert to the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Format the date as a string in the desired format
        String formattedDate = dateTime.format(formatter);
        return formattedDate;
    }

    private FetchedData fetchWeatherData(String latitude, String longitude) {
        Mono<FetchedData> weatherData = this.weatherDataFetcherClient.fetchData(latitude, longitude);
        return weatherData.block();
    }
}
