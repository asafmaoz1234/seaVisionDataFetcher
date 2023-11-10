package com.seavision.seavisiondatafetcher.handlers;

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

    public Handler(DataProcessorService dataProcessorService, LocationsRepository locationsRepository, WeatherDataFetcherClient weatherDataFetcherClient) {
        this.dataProcessorService = dataProcessorService;
        this.locationsRepository = locationsRepository;
        this.weatherDataFetcherClient = weatherDataFetcherClient;
    }

    public String handleRequest() throws DbException {
        List<Locations> locations;
        try {
            locations = locationsRepository.findAll();
            logger.info("found "+ locations.size() +"locations");
        } catch (Exception e) {
            logger.severe("failed locationsRepository.findAll(): " + e.getMessage());
            throw new DbException(e.getMessage());
        }
        if(locations.isEmpty()) {
            logger.severe("Empty locations");
            return "Empty locations";
        }
        // Convert the list of locations to a Flux
        Flux<Locations> requests = Flux.fromIterable(locations);
        Flux<FetchedData> responses = requests
                .flatMap(location -> weatherDataFetcherClient.fetchData(location.getLatitude(), location.getLongitude())
                        .subscribeOn(Schedulers.parallel())
                );
        List<FetchedData> fetchedDataList = responses.collectList().block(); // Block until all responses are received
        if (fetchedDataList != null && !fetchedDataList.isEmpty()) {
            logger.info("found: " + fetchedDataList.size() + " responses");
            fetchedDataList.forEach(dataProcessorService::processData);
            return "Successful Done";
        }
        return "Empty results Done";
    }

}
