package com.seavision.seavisiondatafetcher.services;

import com.seavision.seavisiondatafetcher.clients.WeatherDataFetcherClient;
import com.seavision.seavisiondatafetcher.dtos.DataPerHour;
import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import com.seavision.seavisiondatafetcher.dtos.Meta;
import com.seavision.seavisiondatafetcher.pojos.GeneralUtilities;
import com.seavision.seavisiondatafetcher.repositories.WeatherData;
import com.seavision.seavisiondatafetcher.repositories.WeatherRepository;
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
        List<WeatherData> weatherDataList = this.extractWeatherData(fetchedData.getHours(), fetchedData.getMeta());
        this.weatherRepository.saveAll(weatherDataList);
    }

    private List<WeatherData> extractWeatherData(List<DataPerHour> dataPerHourList, Meta meta) {
        return dataPerHourList.stream()
                .map(dataPerHour -> new WeatherData()
                        .setLatitude(String.valueOf(meta.getLat()))
                        .setLongitude(String.valueOf(meta.getLng()))
                        .setMetricsDay(GeneralUtilities.convertToDDMMYYYY(dataPerHour.getTime()))
                        .setWaveHeight(dataPerHour.getWaveHeight().getNoaa()))
                .collect(Collectors.toList());
    }


    protected FetchedData fetchWeatherData(String latitude, String longitude) {
        Mono<FetchedData> weatherData = this.weatherDataFetcherClient.fetchData(latitude, longitude);
        return weatherData.block();
    }
}
