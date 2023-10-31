package com.seavision.seavisiondatafetcher;

import com.seavision.seavisiondatafetcher.entities.Locations;
import com.seavision.seavisiondatafetcher.repositories.LocationsRepository;
import com.seavision.seavisiondatafetcher.services.DataProcessorService;
import org.springframework.stereotype.Service;

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
        try {
            locations = locationsRepository.findAll();
        } catch (Exception e) {
            logger.severe("failed locationsRepository.findAll(): "+e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("locations size: "+locations.size());
        locations.forEach(location -> {
            try {
                dataProcessorService.processData(location.getLatitude(), location.getLongitude());
            } catch (Exception e) {
                logger.severe("failed handleRequest: "+e.getMessage());
            }
        });
        return "Done";
    }
}
