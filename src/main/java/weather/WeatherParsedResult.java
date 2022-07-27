package weather;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.simple.JSONObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherParsedResult {
    private String time;
    private JSONObject waveHeight;

    public WeatherParsedResult(String time, JSONObject waveHeight) {
        this.time = time;
        this.waveHeight = waveHeight;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public JSONObject getWaveHeight() {
        return waveHeight;
    }

    public void setWaveHeight(JSONObject waveHeight) {
        this.waveHeight = waveHeight;
    }
}
