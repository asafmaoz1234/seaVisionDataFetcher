package com.seavision.seavisiondatafetcher.dtos;

import java.util.List;

public class Meta {
    private int cost;
    private int dailyQuota;
    private String end;
    private double lat;
    private double lng;
    private List<String> params;
    private int requestCount;
    private List<String> source;
    private String start;

    // Constructors

    public Meta() {
    }

    public Meta(int cost, int dailyQuota, String end, double lat, double lng, List<String> params, int requestCount, List<String> source, String start) {
        this.cost = cost;
        this.dailyQuota = dailyQuota;
        this.end = end;
        this.lat = lat;
        this.lng = lng;
        this.params = params;
        this.requestCount = requestCount;
        this.source = source;
        this.start = start;
    }

    // Setters that return 'this' for fluent chaining
    public Meta cost(int cost) {
        this.cost = cost;
        return this;
    }

    public Meta dailyQuota(int dailyQuota) {
        this.dailyQuota = dailyQuota;
        return this;
    }

    public Meta end(String end) {
        this.end = end;
        return this;
    }

    public Meta lat(double lat) {
        this.lat = lat;
        return this;
    }

    public Meta lng(double lng) {
        this.lng = lng;
        return this;
    }

    public Meta params(List<String> params) {
        this.params = params;
        return this;
    }

    public Meta requestCount(int requestCount) {
        this.requestCount = requestCount;
        return this;
    }

    public Meta source(List<String> source) {
        this.source = source;
        return this;
    }

    public Meta start(String start) {
        this.start = start;
        return this;
    }

    public int getCost() {
        return cost;
    }

    public int getDailyQuota() {
        return dailyQuota;
    }

    public String getEnd() {
        return end;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public List<String> getParams() {
        return params;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public List<String> getSource() {
        return source;
    }

    public String getStart() {
        return start;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "cost=" + cost +
                ", dailyQuota=" + dailyQuota +
                ", end='" + end + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", params=" + params +
                ", requestCount=" + requestCount +
                ", source=" + source +
                ", start='" + start + '\'' +
                '}';
    }
}
