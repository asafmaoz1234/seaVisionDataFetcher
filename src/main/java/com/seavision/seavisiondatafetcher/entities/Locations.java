package com.seavision.seavisiondatafetcher.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Locations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "longitude", nullable = false)
    private String longitude;
    @Column(name = "latitude", nullable = false)
    private String latitude;
    @Column(name = "location_name", nullable = false)
    private String locationName;
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;

    public Locations() {
        super();
    }

    public Long getId() {
        return id;
    }

    public Locations setId(Long id) {
        this.id = id;
        return this;
    }

    public Locations setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public Locations setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public Locations setLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLocationName() {
        return locationName;
    }


    public Boolean getActive() {
        return isActive;
    }

    public Locations setActive(Boolean active) {
        isActive = active;
        return this;
    }

    @Override
    public String toString() {
        return "Locations{" +
                "id=" + id +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", locationName='" + locationName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
