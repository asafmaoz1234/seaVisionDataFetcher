package weather.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import weather.WeatherParsedResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeatherClient {

    public List<WeatherParsedResult> fetchWeatherData() throws IOException, ParseException {
        long end = Instant.now().getEpochSecond();
        long start = Instant.now().minus(Duration.ofHours(72)).getEpochSecond();
        URL url = new URL("https://api.stormglass.io/v2/weather/point?lat=32.578070&lng=34.908739&params=waveHeight&source=noaa&start="+start+"&end="+end);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "fd9a2eb6-0caa-11ed-b6bf-0242ac130002-fd9a2f1a-0caa-11ed-b6bf-0242ac130002");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        JSONObject response = (JSONObject) new JSONParser().parse(content.toString());
        System.out.println(response);
        // TODO clean up client set up to abstract class, set up url params better, parse response to list of objects,
        List<WeatherParsedResult> participantJsonList = new ArrayList<>();
        JSONArray data = (JSONArray) response.get("hours");
        for (int i = 0; i < data.size(); i++) {

        }
        Iterator iterator = ((JSONArray) response.get("hours")).iterator();
        while(iterator.hasNext()) {


        }
        List<WeatherParsedResult> participantJsonList = new ObjectMapper().readValue((JSONArray) response.get("hours"), new TypeReference<List<WeatherParsedResult>>(){});
        System.out.println(participantJsonList);
        return participantJsonList;


//        System.out.println(participantJsonList);
//        System.out.println(response);
//        return participantJsonList;
    }
}
