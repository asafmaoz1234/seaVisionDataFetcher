package com.seavision.seavisiondatafetcher.handlers;

import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import com.seavision.seavisiondatafetcher.entities.Locations;
import com.seavision.seavisiondatafetcher.repositories.LocationsRepository;
import com.seavision.seavisiondatafetcher.services.DataProcessorService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class Handler {
    Logger logger = Logger.getLogger(Handler.class.getName());

    final
    DataProcessorService dataProcessorService;

    final
    LocationsRepository locationsRepository;

    public Handler(DataProcessorService dataProcessorService, LocationsRepository locationsRepository) {
        this.dataProcessorService = dataProcessorService;
        this.locationsRepository = locationsRepository;
    }

    public String handleRequest() {
        List<Locations> locations = null;
        long startTime = System.currentTimeMillis();
        try {
            locations = locationsRepository.findAll();
            logger.info("locations size: " + locations.size());
        } catch (Exception e) {
            logger.severe("failed locationsRepository.findAll(): " + e.getMessage());
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Request for location took " + elapsedTime + " ms");
        // Convert the list of locations to a Flux
        Flux<Locations> requests = Flux.fromIterable(locations);
        logger.info("converted to flux");
        // Use flatMap to send requests based on latitude and longitude
        Flux<FetchedData> responses = requests
                .flatMap(location -> dataProcessorService.fetchWeatherData(location.getLatitude(), location.getLongitude())
                        .subscribeOn(Schedulers.boundedElastic())
                );

        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        System.out.println("Request for fetchWeatherData took " + elapsedTime + " ms");
        // Subscribe to process response
        List<FetchedData> fetchedData = new ArrayList<>();
        responses.subscribe(
                response -> {
                    fetchedData.add(response);
                    System.out.println("Received response: " + response.toString());
                },
                error -> System.err.println("Error: " + error),
                () -> System.out.println("All responses processed")
        );
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        System.out.println("Request for populate list " + elapsedTime + " ms");

        fetchedData.forEach(fetchedData1 -> System.out.println("fetchedData1: " + fetchedData1.toString()));
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        System.out.println("Request for print list " + elapsedTime + " ms");

        return "Done";
    }
}
