package com.seavision.seavisiondatafetcher.pojos;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class GeneralUtilities {


    public static String convertToDDMMYYYY(String inputDate) {
        OffsetDateTime dateTime = OffsetDateTime.parse(inputDate);
        // Create a formatter to convert to the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Format the date as a string in the desired format
        String formattedDate = dateTime.format(formatter);
        return formattedDate;
    }
}
