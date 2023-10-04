package com.seavision.seavisiondatafetcher.repositories;

import com.seavision.seavisiondatafetcher.BaseTest;
import com.seavision.seavisiondatafetcher.entities.Locations;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationsTests extends BaseTest {
    @Autowired
    LocationsRepository locationsRepository;

    List<Locations> locations = Arrays.asList(
            new Locations().setLocationName("north")
                    .setLatitude("53.3498")
                    .setLongitude("6.2603"),
            new Locations().setLocationName("south")
                    .setLatitude("52.3498")
                    .setLongitude("5.2603"),
            new Locations().setLocationName("east")
                    .setLatitude("53.3498")
                    .setLongitude("7.2603"),
            new Locations().setLocationName("west")
                    .setLatitude("53.3498")
                    .setLongitude("5.2603"));


    @BeforeEach
    public void setUp() {
        locationsRepository.deleteAll();
        locationsRepository.saveAll(locations);
    }

    @Test
    public void findAllLocations() {
        List<Locations> response = locationsRepository.findAll();
        assertEquals(response.size(), this.locations.size());
    }

}
