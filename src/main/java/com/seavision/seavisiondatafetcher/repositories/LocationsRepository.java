package com.seavision.seavisiondatafetcher.repositories;

import com.seavision.seavisiondatafetcher.entities.Locations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationsRepository extends JpaRepository<Locations, Long> {
}