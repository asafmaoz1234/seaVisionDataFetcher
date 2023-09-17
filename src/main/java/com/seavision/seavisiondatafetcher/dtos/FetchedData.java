package com.seavision.seavisiondatafetcher.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FetchedData {
    private List<DataPerHour> hours;

    public List<DataPerHour> getHours() {
        return hours;
    }

    public void setHours(List<DataPerHour> hours) {
        this.hours = hours;
    }


    @Override
    public String toString() {
        return "FetchedData{" +
                "hours=" + hours +
                '}';
    }
}
