package com.seavision.seavisiondatafetcher.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FetchedData {
    private List<DataPerHour> hours;

    private Meta meta;

    public FetchedData() {
    }

    public FetchedData(List<DataPerHour> hours, Meta meta) {
        this.hours = hours;
        this.meta = meta;
    }

    public List<DataPerHour> getHours() {
        return hours;
    }

    public void setHours(List<DataPerHour> hours) {
        this.hours = hours;
    }

    public FetchedData setMeta(Meta meta) {
        this.meta = meta;
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    @Override
    public String toString() {
        return "FetchedData{" +
                "hours=" + hours +
                ", meta=" + meta +
                '}';
    }
}
