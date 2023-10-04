package com.seavision.seavisiondatafetcher;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.seavision.seavisiondatafetcher.entities.Locations;
import com.seavision.seavisiondatafetcher.repositories.LocationsRepository;
import com.seavision.seavisiondatafetcher.services.DataProcessorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;

public class Handler  implements RequestHandler<Object, String> {
    Logger logger = Logger.getLogger(Handler.class.getName());

    @Autowired
    DataProcessorService dataProcessorService;

    @Autowired
    LocationsRepository locationsRepository;

    @Override
    public String handleRequest(Object o, Context context) {
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
        return null;
    }
}
