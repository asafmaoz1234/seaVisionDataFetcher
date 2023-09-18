package com.seavision.seavisiondatafetcher.handlers;

import com.seavision.seavisiondatafetcher.BaseTest;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HandlerTest extends BaseTest {

    @Test
    public void allPropertiesLoaded() {
        assertEquals("http://localhost:8080/stubit/check", this.baseUrl);
    }
}
