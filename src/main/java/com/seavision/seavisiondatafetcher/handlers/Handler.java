package com.seavision.seavisiondatafetcher.handlers;

import com.seavision.seavisiondatafetcher.clients.WeatherDataFetcherClient;
import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import com.seavision.seavisiondatafetcher.entities.Locations;
import com.seavision.seavisiondatafetcher.repositories.LocationsRepository;
import com.seavision.seavisiondatafetcher.services.DataProcessorService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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

    final WeatherDataFetcherClient weatherDataFetcherClient;

    public Handler(DataProcessorService dataProcessorService, LocationsRepository locationsRepository, WeatherDataFetcherClient weatherDataFetcherClient) {
        this.dataProcessorService = dataProcessorService;
        this.locationsRepository = locationsRepository;
        this.weatherDataFetcherClient = weatherDataFetcherClient;
    }

    public String handleRequest() {
        List<Locations> locations = null;
        try {
            locations = locationsRepository.findAll();
            logger.info("locations size: " + locations.size());
        } catch (Exception e) {
            logger.severe("failed locationsRepository.findAll(): " + e.getMessage());
            throw new RuntimeException(e);
        }
        // Convert the list of locations to a Flux
        Flux<Locations> requests = Flux.fromIterable(locations);
        logger.info("converted to flux");
        // Use flatMap to send requests based on latitude and longitude
        Flux<FetchedData> responses = requests
                .flatMap(location -> weatherDataFetcherClient.fetchData(location.getLatitude(), location.getLongitude())
                        .subscribeOn(Schedulers.parallel())
                );
        this.getdata(responses).block();


        return "Done";
    }

    private Mono<String> getdata(Flux<FetchedData> responses) {
        return Mono.defer(() -> responses.doOnNext(response -> {
            System.out.println("Received response: " + response.getMeta().toString());
            dataProcessorService.processData(response);
        }).then(Mono.just("Done")));
    }
}
