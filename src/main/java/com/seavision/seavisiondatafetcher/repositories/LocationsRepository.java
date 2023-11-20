package com.seavision.seavisiondatafetcher.repositories;

import com.seavision.seavisiondatafetcher.entities.Locations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationsRepository extends JpaRepository<Locations, Long> {
    List<Locations> findAllByIsActiveIsTrue();
}
