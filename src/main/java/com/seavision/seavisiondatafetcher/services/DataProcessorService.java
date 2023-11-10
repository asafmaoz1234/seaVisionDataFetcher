package com.seavision.seavisiondatafetcher.services;

import com.seavision.seavisiondatafetcher.dtos.DataPerHour;
import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import com.seavision.seavisiondatafetcher.dtos.Meta;
import com.seavision.seavisiondatafetcher.entities.WeatherData;
import com.seavision.seavisiondatafetcher.pojos.GeneralUtilities;
import com.seavision.seavisiondatafetcher.repositories.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class DataProcessorService {
    Logger logger = Logger.getLogger(DataProcessorService.class.getName());

    final
    WeatherRepository weatherRepository;

    public DataProcessorService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void processData(FetchedData fetchedData) {
        if (fetchedData == null || fetchedData.getHours() == null || fetchedData.getHours().isEmpty() || fetchedData.getMeta() == null) {
            logger.info("No data to process");
            return;
        }
        List<WeatherData> weatherDataList = this.extractWeatherData(fetchedData.getHours(), fetchedData.getMeta());
        logger.info("Saving " + weatherDataList.size() + " records");
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

}
