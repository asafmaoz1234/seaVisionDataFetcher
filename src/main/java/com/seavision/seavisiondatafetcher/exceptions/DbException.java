package com.seavision.seavisiondatafetcher.exceptions;

public class DbException extends Exception{
    public DbException(String message) {
        super("Failed on DB "+ message);
    }
}
