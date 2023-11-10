package com.seavision.seavisiondatafetcher.pojos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneralUtilitiesTest {
    @Test
    public void testConvertToDDMMYYYY() {
        // Input date in the format "YYYY-MM-dd'T'HH:mm:ssXXX"
        String inputDate = "2022-09-10T05:00:00+00:00";

        // Expected output in the format "dd/MM/yyyy-HH:mm"
        String expectedOutput = "10/09/2022-05:00";

        // Call the method to convert the input date
        String formattedDate = GeneralUtilities.convertToDDMMYYYY(inputDate);

        // Assert that the formatted date matches the expected output
        assertEquals(expectedOutput, formattedDate);
    }
}