package com.seavision.seavisiondatafetcher.handlers;

import com.seavision.seavisiondatafetcher.PropertiesTest;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HandlerTest extends PropertiesTest {

    @Test
    public void allPropertiesLoaded() {
        assertEquals("https://asafmaoz.com/404", this.baseUrl);
    }
}
