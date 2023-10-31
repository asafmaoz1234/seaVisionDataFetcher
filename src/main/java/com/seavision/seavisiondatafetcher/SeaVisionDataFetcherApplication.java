package com.seavision.seavisiondatafetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;
import java.util.logging.Logger;

@SpringBootApplication
public class SeaVisionDataFetcherApplication {
    Logger logger = Logger.getLogger(Handler.class.getName());

    final
    Handler handler;

    public SeaVisionDataFetcherApplication(Handler handler) {
        this.handler = handler;
    }

    public static void main(String[] args) {
        SpringApplication.run(SeaVisionDataFetcherApplication.class, args);
    }

    @Bean
    public Function<String,String> handle() {
        logger.info("handle() called");
        this.handler.handleRequest();
        return null;
    }
}
