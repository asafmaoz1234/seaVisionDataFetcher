package com.seavision.seavisiondatafetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seavision.seavisiondatafetcher.dtos.FetchedData;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.FileReader;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
public abstract class BaseTest {

    @Value("${weather-client.base-url}")
    protected String baseUrl;

    @Value("${weather-client.auth-key}")
    protected String authKey;

    @Value("${weather-client.request-params}")
    protected String reqParams;

    private final String pathToSample = "src/test/resources/metrics_data_sample.json";

    public FetchedData loadSampleFetchedData() throws IOException {
        // Create an ObjectMapper instance
        // Read JSON from the file and convert it to an object
        return new ObjectMapper().readValue(new File(pathToSample), FetchedData.class);
    }

    public String loadSampleText() throws IOException {
        // Create a StringBuilder to store the JSON data
        StringWriter jsonContent = new StringWriter();
        // Open and read the JSON file using BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToSample))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        }
        // Convert the StringBuilder to a String
        return jsonContent.toString();
    }

}
