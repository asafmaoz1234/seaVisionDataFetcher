package weather.pojos;

import static weather.config.EnvParams.API_REQUEST_POINT_LAT;
import static weather.config.EnvParams.API_REQUEST_POINT_LNG;

public class HandlerResponse {
    private String message;
    private SnorkelingResult snorkelingResults;

    public HandlerResponse() {
        this.message = "Invalid response";
    }

    public HandlerResponse(SnorkelingResult snorkelingResult) {
        this.message = "Nope, maybe tomorrow.";
        if(snorkelingResult.canGo()) {
            this.message = "Yes! got snorkeling";
        }
        this.snorkelingResults = snorkelingResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SnorkelingResult getSnorkelingResults() {
        return snorkelingResults;
    }

    public void setSnorkelingResults(SnorkelingResult snorkelingResults) {
        this.snorkelingResults = snorkelingResults;
    }

    @Override
    public String toString() {
        return "{" +
                "message='" + message + '\'' +
                ", snorkelingResults=" + snorkelingResults +
                ", pointChecked=" +  API_REQUEST_POINT_LAT+API_REQUEST_POINT_LNG +
                '}';
    }
}
