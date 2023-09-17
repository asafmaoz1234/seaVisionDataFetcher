package com.seavision.seavisiondatafetcher.dtos;

public class DataPerHour {
    private String time;
    private WaveData waveHeight;

    public DataPerHour(){}
    public DataPerHour(String time, WaveData waveHeight) {
        this.time = time;
        this.waveHeight = waveHeight;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public WaveData getWaveHeight() {
        return waveHeight;
    }

    public void setWaveHeight(WaveData waveHeight) {
        this.waveHeight = waveHeight;
    }

    @Override
    public String toString() {
        return "DataPerHour{" +
                "time='" + time + '\'' +
//                ", waveHeight=" + waveHeight +
                '}';
    }
}
