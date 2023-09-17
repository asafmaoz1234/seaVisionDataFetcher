package com.seavision.seavisiondatafetcher.dtos;

public class WaveData {
    private Double noaa;

    public WaveData(){}
    public WaveData(Double noaa) {
        this.noaa = noaa;
    }

    public Double getNoaa() {
        return noaa;
    }

    public void setNoaa(Double noaa) {
        this.noaa = noaa;
    }

    @Override
    public String toString() {
        return "WaveData{" +
                "noaa=" + noaa +
                '}';
    }
}
