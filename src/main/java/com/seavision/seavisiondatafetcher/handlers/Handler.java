package com.seavision.seavisiondatafetcher.handlers;

import com.seavision.seavisiondatafetcher.clients.SqsClient;
import com.seavision.seavisiondatafetcher.clients.WeatherDataFetcherClient;
import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import com.seavision.seavisiondatafetcher.entities.Locations;
import com.seavision.seavisiondatafetcher.exceptions.DbException;
import com.seavision.seavisiondatafetcher.repositories.LocationsRepository;
import com.seavision.seavisiondatafetcher.services.DataProcessorService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class Handler {
    Logger logger = Logger.getLogger(Handler.class.getName());

    final
    DataProcessorService dataProcessorService;

    final
    LocationsRepository locationsRepository;

    final
    WeatherDataFetcherClient weatherDataFetcherClient;

    final
    SqsClient sqsClient;

    public Handler(DataProcessorService dataProcessorService, LocationsRepository locationsRepository, WeatherDataFetcherClient weatherDataFetcherClient, SqsClient sqsClient) {
        this.dataProcessorService = dataProcessorService;
        this.locationsRepository = locationsRepository;
        this.weatherDataFetcherClient = weatherDataFetcherClient;
        this.sqsClient = sqsClient;
    }

    public String handleRequest() throws DbException {
        List<Locations> locations = fetchLocations();
        if (locations.isEmpty()) {
            this.logger.severe("Empty locations");
            this.sqsClient.publishMessage(null, "Empty locations");
            return "Empty locations";
        }

        Map<Long, FetchedData> fetchedDataList = fetchWeatherData(locations);
        if (fetchedDataList.isEmpty()) {
            this.logger.severe("Empty results");
            this.sqsClient.publishMessage(null, "Empty results");
            return "Empty results Done";
        }

        processFetchedData(fetchedDataList);
        this.sqsClient.publishMessage(null, "Successful Done");
        return "Successful Done";
    }

    private List<Locations> fetchLocations() throws DbException {
        try {
            List<Locations> locations = locationsRepository.findAll();
            this.logger.info("Found " + locations.size() + " locations");
            return locations;
        } catch (Exception e) {
            this.logger.severe("Failed locationsRepository.findAll(): " + e.getMessage());
            this.sqsClient.publishMessage(null, "DB ERROR");
            throw new DbException(e.getMessage());
        }
    }

    private Map<Long, FetchedData> fetchWeatherData(List<Locations> locations) {
        Flux<Locations> requests = Flux.fromIterable(locations);
        Flux<LocationDataPair> responses = requests.flatMap(location ->
                weatherDataFetcherClient.fetchData(location.getLatitude(), location.getLongitude())
                        .subscribeOn(Schedulers.parallel())
                        .map(data -> new LocationDataPair(location.getId(), data))
        );

        return responses.collectMap(LocationDataPair::locationId, LocationDataPair::fetchedData).block();
    }


    private void processFetchedData(Map<Long, FetchedData> fetchedDataMap) {
        this.logger.info("Found: " + fetchedDataMap.size() + " responses");
        fetchedDataMap.forEach((locationId, locationData) ->
                this.dataProcessorService.processData(locationData, locationId));
    }

    private record LocationDataPair(Long locationId, FetchedData fetchedData) {
    }
}
