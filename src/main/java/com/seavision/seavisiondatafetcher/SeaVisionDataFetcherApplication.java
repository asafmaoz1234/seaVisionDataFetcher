package com.seavision.seavisiondatafetcher;

import com.seavision.seavisiondatafetcher.exceptions.DbException;
import com.seavision.seavisiondatafetcher.handlers.Handler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;
import java.util.logging.Logger;

@SpringBootApplication
public class SeaVisionDataFetcherApplication {
    Logger logger = Logger.getLogger("SeaVisionDataFetcherApplication");


    public static void main(String[] args) {
        SpringApplication.run(SeaVisionDataFetcherApplication.class, args);
    }

    @Bean
    public Function<String,String> handle(Handler handler) {
        logger.info("handle() called");
        return value-> {
            try {
                return handler.handleRequest();
            } catch (DbException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
